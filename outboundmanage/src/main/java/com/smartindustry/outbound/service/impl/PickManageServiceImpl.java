package com.smartindustry.outbound.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.smartindustry.common.bo.om.LabelRecommendBO;
import com.smartindustry.common.bo.om.PickBodyBO;
import com.smartindustry.common.bo.om.PickHeadBO;
import com.smartindustry.common.mapper.om.*;
import com.smartindustry.common.mapper.si.ConfigMapper;
import com.smartindustry.common.mapper.si.MaterialAttributeMapper;
import com.smartindustry.common.mapper.si.PrintLabelMapper;
import com.smartindustry.common.mapper.si.StorageLabelMapper;
import com.smartindustry.common.pojo.am.UserPO;
import com.smartindustry.common.pojo.om.*;
import com.smartindustry.common.security.service.TokenService;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.outbound.constant.OutboundConstant;
import com.smartindustry.outbound.dto.OperateDTO;
import com.smartindustry.outbound.util.BusinessUtil;
import com.smartindustry.outbound.util.OmNoUtil;
import com.smartindustry.outbound.vo.*;
import com.smartindustry.outbound.service.IPickManageService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: xiahui
 * @date: Created in 2020/7/14 19:02
 * @description: 拣货单管理
 * @version: 1.0
 */
@EnableTransactionManagement
@Service
public class PickManageServiceImpl implements IPickManageService {
    @Autowired
    private PickHeadMapper pickHeadMapper;
    @Autowired
    private PickBodyMapper pickBodyMapper;
    @Autowired
    private PickLabelMapper pickLabelMapper;
    @Autowired
    private LabelRecommendMapper labelRecommendMapper;
    @Autowired
    private StorageLabelMapper storageLabelMapper;
    @Autowired
    private PrintLabelMapper printLabelMapper;
    @Autowired
    private OutboundMapper outboundMapper;
    @Autowired
    private PickCheckMapper pickCheckMapper;
    @Autowired
    private OutboundRecordMapper outboundRecordMapper;
    @Autowired
    TokenService tokenService;
    @Autowired
    ConfigMapper configMapper;
    @Autowired
    private MaterialAttributeMapper materialAttributeMapper;

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData) {
        Page<PickHeadBO> page = PageQueryUtil.startPage(reqData);
        List<PickHeadBO> bos = pickHeadMapper.pageQuery(reqData);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), PickHeadVO.convertBO(bos)));
    }

    @Override
    public ResultVO pageQueryPickHead(int pageNum, int pageSize, Map<String, Object> reqMap) {
        Page<PickHeadPO> page = PageHelper.startPage(pageNum, pageSize);
        List<PickHeadPO> vos = pickHeadMapper.pageQueryPickHeadMsg(reqMap);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), PickHeadVO.convert(vos)));
    }

    @Override
    public ResultVO queryPickGoods(int pageNum, int pageSize, Map<String, Object> reqMap) {
        Page<PickHeadPO> page = PageHelper.startPage(pageNum, pageSize);
        List<PickHeadPO> vos = pickHeadMapper.queryPickGoods(reqMap);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), PickHeadVO.convert(vos)));
    }

    @Override
    public ResultVO outOrderCheck(Map<String, Object> reqData) {
        Page<PickHeadPO> page = PageQueryUtil.startPage(reqData);
        List<PickHeadPO> bos = pickHeadMapper.outOrderCheck(reqData);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), PickHeadVO.convert(bos)));
    }

    @Override
    public ResultVO queryByPhId(Long pickHeadId) {
        PickHeadPO po = pickHeadMapper.selectByPrimaryKey(pickHeadId);
        return ResultVO.ok().setData(PickHeadVO.convert(po));
    }

    @Override
    public ResultVO materialLoss(Map<String, Object> reqMap) {
        List<PickBodyBO> bos = pickHeadMapper.materialLoss(reqMap);
        return ResultVO.ok().setData(LackMaterialVO.convert(bos));
    }

    @Override
    public ResultVO queryExItems(Long pickHeadId) {
        // 若扫描了未推荐的PID,则异常列表只显示，扫描了其他推荐的PID
        List<PickHeadBO> noRecommend = pickHeadMapper.queryNoRecommend(pickHeadId);
        if (null == noRecommend || noRecommend.size() == 0) {
            // 目前未有异常信息
            return ResultVO.ok().setData(noRecommend);
        }
        // 若已拣货量大于需求量时，将未扫描优先推荐的pid以及扫描了其他推荐的pid
        //(1) 先查询出所有的推荐的pid
        List<PickHeadBO> reList = pickHeadMapper.queryRecommend(pickHeadId);
        if (null == reList || reList.size() == 0) {
            // 当前工单拣货单没有推荐的pid
            return new ResultVO(1014);
        }
        Map<String, String> map = reList.stream()
                .collect(HashMap::new, (m, v) -> m.put(v.getMaterialNo(), v.getRecommendPid()), HashMap::putAll);
        //(2) 再查询出目前工单已经使用的推荐pid,这里必须是  拣货量大于 需求量才查询
        List<PickHeadBO> useList = pickHeadMapper.queryAllRePid(pickHeadId);
        if (null != useList && useList.size() != 0) {
            // 当需求量满足时，没有使用任何推荐pid
            for (PickHeadBO bo : useList) {
                String materialNo = bo.getMaterialNo();
                boolean containsKey = map.containsKey(materialNo);
                if (containsKey) {
                    List<String> allPidList = Arrays.asList(map.get(materialNo).split(","));
                    List<String> rePidList = Arrays.asList(bo.getRecommendPid().split(","));
                    bo.setRecommendPid(StringUtils.join(allPidList.stream()
                            .filter(s -> !rePidList.contains(s))
                            .collect(Collectors.toList()), ","));
                }
            }
        } else {
            for (PickHeadBO bo : noRecommend) {
                String exception = pickBodyMapper.queryException(pickHeadId, bo.getMaterialId());
                bo.setAberrantDesc(exception);
            }
            return ResultVO.ok().setData(AberrantItemsVO.convert(noRecommend));
        }
        //(3) 推荐未使用的进行相减，以及拼接其他未推荐的pid
        Map<String, String> useMap = useList.stream()
                .collect(HashMap::new, (m, v) -> m.put(v.getMaterialNo(), v.getRecommendPid()), HashMap::putAll);
        // 将未使用推荐和已使用未推荐进行组合
        for (PickHeadBO bo : noRecommend) {
            String materialNo = bo.getMaterialNo();
            boolean useKey = useMap.containsKey(materialNo);
            if (useKey) {
                bo.setRecommendPid(useMap.get(materialNo));
            }
            String exception = pickBodyMapper.queryException(pickHeadId, bo.getMaterialId());
            bo.setAberrantDesc(exception);
        }
        return ResultVO.ok().setData(AberrantItemsVO.convert(noRecommend));
    }

    @Override
    public ResultVO detail(OperateDTO dto) {
        PickHeadPO headPO = pickHeadMapper.selectByPrimaryKey(dto.getPhid());
        if (null == headPO) {
            return new ResultVO(1002);
        }

        PickDetailVO vo = new PickDetailVO();
        vo.setPickHeadVO(headPO);

        List<PickBodyBO> bodyBOs = pickBodyMapper.queryByHeadId(dto.getPhid());
        List<PickDetailVO.PickItemVO> itemVOs = new ArrayList<>(bodyBOs.size());
        for (PickBodyBO bo : bodyBOs) {
            PickDetailVO.PickItemVO itemVO = new PickDetailVO.PickItemVO();
            itemVO.setBody(PickBodyVO.convert(bo));
            // 优先推荐
            List<LabelRecommendBO> labelRecommendBOs = labelRecommendMapper.queryByPbid(bo.getPickBodyId());
            Map<String, String> recommendVO = new HashMap<>();
            for (LabelRecommendBO recommendBO : labelRecommendBOs) {
                String key = recommendBO.getLocationNo();
                if (recommendVO.containsKey(key)) {
                    recommendVO.put(key, recommendVO.get(key) + "," + recommendBO.getPackageId());
                } else {
                    recommendVO.put(key, recommendBO.getPackageId());
                }
            }
            List<String> recommendVOs = new ArrayList<>();
            for (Map.Entry<String, String> entry : recommendVO.entrySet()) {
                recommendVOs.add(entry.getKey() + " " + entry.getValue());
            }
            itemVO.setRecommend(recommendVOs);
            // 其他库位
            List<StorageLabelBO> notRecommendBOs = storageLabelMapper.queryNotRecommend(headPO.getSourceNo(), bo.getMaterialId());
            Set<String> notRecommendVO = new HashSet<>();
            for (StorageLabelBO notRecommendBO : notRecommendBOs) {
                notRecommendVO.add(notRecommendBO.getLocationNo());
            }
            itemVO.setOther(new ArrayList<>(notRecommendVO));
            itemVOs.add(itemVO);
        }
        vo.setItems(itemVOs);

        return ResultVO.ok().setData(vo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO pickPidOut(Long pickHeadId, String packageId) {

        //1.首先根据输入的PID,得到相应PID的信息，进行展示
        PrintLabelBO bo = pickHeadMapper.pickPid(packageId);
        PickHeadPO po = pickHeadMapper.selectByPrimaryKey(pickHeadId);
        if (bo == null) {
            // 提示没有这个PID号
            return new ResultVO(1015);
        }
        //2 若当前输入的PID已经被自身或者其他扫码入库，则提示无法重复扫码
        PickLabelPO pickLabelPO = pickLabelMapper.queryPickLabel(bo.getPrintLabelId());
        if (pickLabelPO != null) {
            return new ResultVO(1018);
        }
        //当销售，生产，采购强关联时，工单所扫码的PID来源必须是销售采购来源
        ConfigPO configPO = configMapper.queryByKey(OutboundConstant.K_PID_RELATE);
        if (null != configPO && OutboundConstant.V_YES.equals(configPO.getConfigValue())) {
            //若输入的PID并不属于该工单对应采购单的物料范围，则提示该物料并不属于该工单
            String ono = pickHeadMapper.queryOnoByPid(bo.getPrintLabelId());
            if (ono == null || !ono.equals(po.getSourceNo())) {
                return new ResultVO(1016);
            }
        }
        // 判断当前物料不在拣货清单中，则提示 该物料并不在出库清单中
        List<String> maList = pickHeadMapper.judgeMaterial(pickHeadId);
        boolean flag = maList.contains(bo.getMaterialNo());
        if (!flag) {
            return new ResultVO(1017);
        }

        //2.将拣货单表体表中的已拣量作加操作
        pickHeadMapper.addPickNum(pickHeadId, bo.getMaterialId(), bo.getNum());
        //3.查看扫码的PID是否在推荐的库位标签表中是否存在推荐的PID,存在则更新拣货标签表中的是否推荐标志位
        List<String> reList = pickHeadMapper.queryReOnlyPid(pickHeadId);
        boolean flagOne = reList.contains(packageId);
        Byte recommend = flagOne ? (byte) 1 : (byte) 2;
        PickLabelPO pickLabelPo = new PickLabelPO();
        pickLabelPo.setPickHeadId(pickHeadId);
        pickLabelPo.setPrintLabelId(bo.getPrintLabelId());
        pickLabelPo.setRecommend(recommend);
        pickLabelPo.setCreateTime(new Date());
        pickHeadMapper.insertPickLabel(pickLabelPo);
        Integer flagTwo = pickHeadMapper.judgeIsPick(pickHeadId);
        // 更新拣货状态为物料拣货
        int result = (flagTwo != null) ? pickHeadMapper.updateStatus(pickHeadId, OutboundConstant.MATERIAL_STATUS_PICK, new Date()) : 0;
        //根据当前拣货扫码的类型进行库内标签表的状态切换
        // 先推荐
        List<Long> plIds = new ArrayList<>();
        plIds.add(bo.getPrintLabelId());
        BusinessUtil businessUtil = new BusinessUtil();
        switch (po.getSourceType()){
            case OutboundConstant.TYPE_OUT_WORK:
                businessUtil.updateStorageLabel(plIds,OutboundConstant.TYPE_STORAGE_LABEL_PICK,storageLabelMapper);
                break;
            case OutboundConstant.TYPE_OUT_SHIP:
                businessUtil.updateStorageLabel(plIds,OutboundConstant.TYPE_STORAGE_LABEL_SHIP,storageLabelMapper);
                break;
            case OutboundConstant.TYPE_OUT_OTHER:
                businessUtil.updateStorageLabel(plIds,OutboundConstant.TYPE_STORAGE_LABEL_TRANSFER,storageLabelMapper);
                break;
            default:
                break;
        }

        return ResultVO.ok().setData(ScanOutVO.convert(bo));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO packageIdDiv(Long printLabelId, Integer num) {

        //(2) 将扫描的pid的dr值设为2，并且按照分料数量分成两个pid
        PrintLabelPO po = printLabelMapper.selectByPrimaryKey(printLabelId);
        if (num >= po.getNum()) {
            //输入的拆分量大于原有量
            return new ResultVO(1019);
        }
        PrintLabelPO poDivOne = new PrintLabelPO();
        PrintLabelPO poDivTwo = new PrintLabelPO();
        BeanUtils.copyProperties(po, poDivOne, "printLabelId", "dr");
        BeanUtils.copyProperties(po, poDivTwo, "printLabelId", "dr");
        // 生成分料pid1
        Date divOneTime = new Date();
        poDivOne.setCreateTime(divOneTime);
        int curNumOne = OmNoUtil.getLabelNum(printLabelMapper, null, divOneTime);
        poDivOne.setPackageId(OmNoUtil.genLabelNo(null, divOneTime, ++curNumOne));
        poDivOne.setRelatePackageId(po.getPackageId());
        poDivOne.setNum(num);
        poDivOne.setDr((byte) 1);
        printLabelMapper.insert(poDivOne);

        // 生成分料pid2
        Date divTwoTime = new Date();
        poDivOne.setCreateTime(divTwoTime);
        int curNumTwo = OmNoUtil.getLabelNum(printLabelMapper, null, divTwoTime);
        poDivTwo.setPackageId(OmNoUtil.genLabelNo(null, divTwoTime, ++curNumTwo));
        poDivTwo.setRelatePackageId(po.getPackageId());
        poDivTwo.setNum(po.getNum() - num);
        poDivTwo.setDr((byte) 1);
        printLabelMapper.insert(poDivTwo);
        po.setDr((byte) 2);
        printLabelMapper.updateByPrimaryKey(po);

        // 同时更新库位标签表
        StorageLabelPO po1 = storageLabelMapper.queryByPlid(printLabelId);
        if(po1 == null){
            // 当前PID不存在库位标签
            return new ResultVO(1002);
        }
        StorageLabelPO po2 = new StorageLabelPO();
        StorageLabelPO po3 = new StorageLabelPO();
        BeanUtils.copyProperties(po1,po2,"storageLabelId");
        BeanUtils.copyProperties(po1,po3,"storageLabelId");

        po2.setPrintLabelId(poDivOne.getPrintLabelId());
        po2.setPackageId(poDivOne.getPackageId());
        po2.setStorageNum(poDivOne.getNum());
        storageLabelMapper.insert(po2);
        po3.setPrintLabelId(poDivTwo.getPrintLabelId());
        po3.setPackageId(poDivTwo.getPackageId());
        po3.setStorageNum(poDivTwo.getNum());
        storageLabelMapper.insert(po3);
        storageLabelMapper.deleteByPrimaryKey(po1.getStorageLabelId());
        return ResultVO.ok();
    }

    @Override
    public ResultVO showMsgByPid(String packageId) {
        PrintLabelBO bo = pickHeadMapper.pickPid(packageId);
        if (bo == null) {
            // PID不存在
            return new ResultVO(1015);
        }
        MaterialAttributePO attributePO = materialAttributeMapper.queryByMid(bo.getMaterialId());
        if (null != attributePO && null != attributePO.getPickSplit() && attributePO.getPickSplit() == 1) {
            return new ResultVO(1026);
        }
        //判断当前PID的是否已在某工单拣货单扫码列表中
        Integer resultIn = pickHeadMapper.judgePidInPhid(packageId);
        if (resultIn != null) {
            return new ResultVO(1021);
        }
        return ResultVO.ok().setData(ScanOutVO.convert(bo));
    }

    @Override
    public ResultVO showScanItems(int pageNum, int pageSize, Long pickHeadId) {
        Page<PrintLabelBO> page = PageHelper.startPage(pageNum, pageSize);
        List<PrintLabelBO> bos = pickHeadMapper.showScanItems(pickHeadId);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), ScanOutVO.convert(bos)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO deleteScanPid(Long pickHeadId, Long printLabelId) {
        pickHeadMapper.deleteScanPid(pickHeadId, printLabelId);
        // 删除的时候需要将已拣货量相应的减去
        PrintLabelPO po = printLabelMapper.selectByPrimaryKey(printLabelId);
        pickHeadMapper.updatePickNum(pickHeadId, po.getNum(), po.getMaterialId());
        Integer flag = pickHeadMapper.judgeIsPick(pickHeadId);

        if (null == flag) {
            pickHeadMapper.updateStatus(pickHeadId, OutboundConstant.MATERIAL_STATUS_UNPROCESSED, new Date());
        }

        return ResultVO.ok();
    }

    @Override
    public ResultVO printLabelSplit(String packageId) {
        List<PrintLabelBO> bos = pickHeadMapper.printLabelSplit(packageId);
        return ResultVO.ok().setData(PrintSplitVO.convert(bos));
    }

    @Override
    public ResultVO judgeStatus(Long pickHeadId) {
        // 当前工单拣货单id所关联的拣货标签表拥有数据,那么正处于物料拣货状态
        Integer flag = pickHeadMapper.judgeIsPick(pickHeadId);
        int result = (flag == 1) ? pickHeadMapper.updateStatus(pickHeadId, OutboundConstant.MATERIAL_STATUS_PICK, new Date()) : 0;
        return ResultVO.ok();
    }


    @Override
    public ResultVO updateException(Long pickHeadId, Long materialId, String exception) {
        pickBodyMapper.updateException(pickHeadId, materialId, exception);
        return ResultVO.ok();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO outBoundItems(Long pickHeadId) {
        UserPO user = tokenService.getLoginUser();
        PickHeadPO po1 = pickHeadMapper.selectByPrimaryKey(pickHeadId);
        //1 当形成出库单，由于物料欠缺，异常数据，则由物料拣货10变成工单审核15
        int statusCode = 0;
        Integer resultEx = pickHeadMapper.judgeIsEx(pickHeadId);
        Integer resultLack = pickHeadMapper.judgeIsLack(pickHeadId);
        OutboundPO po = new OutboundPO();
        po.setPickHeadId(pickHeadId);
        Date date = new Date();
        po.setOutboundNo(OmNoUtil.getOutboundNo(outboundMapper, OmNoUtil.OUTBOUND, date));
        po.setStatus(OutboundConstant.OUTBOUND_STATUS_WAIT);
        po.setSourceNo(po1.getPickNo());
        po.setCreateTime(date);
        po.setDr((byte) 1);
        if(OutboundConstant.TYPE_TRANSFER.equals(po1.getSourceType())){
            pickHeadMapper.updateStatus(pickHeadId, OutboundConstant.MATERIAL_STATUS_STORAGE, new Date());
            po.setSourceType(OutboundConstant.TYPE_OUT_OTHER);
            outboundMapper.insert(po);
            outboundRecordMapper.insert(new OutboundRecordPO(pickHeadId, null, user.getUserId(), user.getName(), OutboundConstant.RECORD_SUBMIT, OutboundConstant.MATERIAL_STATUS_PICK));
            outboundRecordMapper.insert(new OutboundRecordPO(pickHeadId, po.getOutboundId(), user.getUserId(), user.getName(), OutboundConstant.RECORD_ADD, OutboundConstant.MATERIAL_STATUS_STORAGE));
        }else {
            if (resultEx != null || resultLack != null) {
                pickHeadMapper.updateStatus(pickHeadId, OutboundConstant.MATERIAL_STATUS_CHECK, new Date());
                PickCheckPO po2 = new PickCheckPO();
                po2.setPickHeadId(pickHeadId);
                po2.setStatus(OutboundConstant.OUTBOUND_STATUS_WAIT);
                pickCheckMapper.insert(po2);
                statusCode = 1;
                // 当欠料异常形成出库单，将新增审核操作记录到操作记录表中
                outboundRecordMapper.insert(new OutboundRecordPO(pickHeadId, null, user.getUserId(), user.getName(), OutboundConstant.RECORD_SUBMIT, OutboundConstant.MATERIAL_STATUS_PICK));
                outboundRecordMapper.insert(new OutboundRecordPO(pickHeadId, null, user.getUserId(), user.getName(), OutboundConstant.RECORD_ADD, OutboundConstant.MATERIAL_STATUS_CHECK));
            } else {
                pickHeadMapper.updateStatus(pickHeadId, OutboundConstant.MATERIAL_STATUS_STORAGE, new Date());
                po.setSourceType(OutboundConstant.TYPE_OUT_WORK);
                outboundMapper.insert(po);
                outboundRecordMapper.insert(new OutboundRecordPO(pickHeadId, null, user.getUserId(), user.getName(), OutboundConstant.RECORD_SUBMIT, OutboundConstant.MATERIAL_STATUS_PICK));
                outboundRecordMapper.insert(new OutboundRecordPO(pickHeadId, po.getOutboundId(), user.getUserId(), user.getName(), OutboundConstant.RECORD_ADD, OutboundConstant.MATERIAL_STATUS_STORAGE));
            }
        }
        //2 当形成出库单，在不欠料的情况下，则由物料拣货10变成物料出库30
        return ResultVO.ok().setData(statusCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO deleteSplit(String packageId) {
        //如果当前pid的关联子pid已经被使用，那么子pid不能被删除
        // 查询当前PID的子pid
        List<String> list = pickHeadMapper.queryChildPid(packageId);
        //判断当前PID的是否已在某工单拣货单扫码列表中
        for (String pid : list) {
            Integer resultInOne = pickHeadMapper.judgePidInPhid(pid);
            if (resultInOne != null) {
                return new ResultVO(1022);
            }
        }
        pickHeadMapper.deletePid(packageId);
        pickHeadMapper.resumePid(packageId);
        return ResultVO.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO disAgree(Long pickHeadId, Byte status, String message) {
        UserPO user = tokenService.getLoginUser();
        PickCheckPO po = new PickCheckPO();
        po.setPickHeadId(pickHeadId);
        po.setRemark(message);
        if (status == null) {
            //OQC检测时的驳回
            pickHeadMapper.updateStatus(pickHeadId, OutboundConstant.MATERIAL_STATUS_RETURN, new Date());
            po.setStatus(OutboundConstant.TURN_DOWN_CANCEL);
            pickCheckMapper.updateByPrimaryKey(po);
            outboundRecordMapper.insert(new OutboundRecordPO(pickHeadId, null, user.getUserId(), user.getName(), OutboundConstant.RECORD_DISAGREE, OutboundConstant.MATERIAL_STATUS_CHECK));
        } else if (status.equals(OutboundConstant.MATERIAL_STATUS_WAIT)) {
            //等齐套发货
            pickHeadMapper.updateStatus(pickHeadId, OutboundConstant.MATERIAL_STATUS_PICK, new Date());
            pickCheckMapper.deleteByPrimaryKey(pickHeadId);
            outboundRecordMapper.insert(new OutboundRecordPO(pickHeadId, null, user.getUserId(), user.getName(), OutboundConstant.RECORD_DISAGREE, OutboundConstant.MATERIAL_STATUS_CHECK));
        } else if (status.equals(OutboundConstant.MATERIAL_STATUS_RETURN)) {
            //取消发货，退货仓库
            pickHeadMapper.updateStatus(pickHeadId, OutboundConstant.MATERIAL_STATUS_RETURN, new Date());
            po.setStatus(OutboundConstant.TURN_DOWN_CANCEL);
            pickCheckMapper.updateByPrimaryKey(po);
            outboundRecordMapper.insert(new OutboundRecordPO(pickHeadId, null, user.getUserId(), user.getName(), OutboundConstant.RECORD_DISAGREE, OutboundConstant.MATERIAL_STATUS_CHECK));
        }
        return ResultVO.ok();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO agreeOutBound(Long pickHeadId) {
        UserPO user = tokenService.getLoginUser();
        PickHeadPO po1 = pickHeadMapper.selectByPrimaryKey(pickHeadId);
        // 欠料出库，将物料状态改成“物料出库”
        pickHeadMapper.updateStatus(pickHeadId, OutboundConstant.MATERIAL_STATUS_STORAGE, new Date());
        // 形成出库单
        OutboundPO po = new OutboundPO();
        po.setPickHeadId(pickHeadId);
        Date date = new Date();
        po.setOutboundNo(OmNoUtil.getOutboundNo(outboundMapper, OmNoUtil.OUTBOUND, date));
        po.setStatus(OutboundConstant.OUTBOUND_STATUS_WAIT);
        if(OutboundConstant.TYPE_PICK.equals(po1.getSourceType())){
            po.setSourceNo(po1.getPickNo());
            po.setSourceType(OutboundConstant.TYPE_OUT_WORK);
        }else {
            po.setSourceNo(po1.getPickNo());
            po.setSourceType(OutboundConstant.TYPE_OUT_SHIP);
        }
        po.setCreateTime(date);
        po.setDr((byte) 1);
        outboundMapper.insert(po);
        outboundRecordMapper.insert(new OutboundRecordPO(pickHeadId, null, user.getUserId(), user.getName(), OutboundConstant.RECORD_AGREE, OutboundConstant.MATERIAL_STATUS_CHECK));
        outboundRecordMapper.insert(new OutboundRecordPO(pickHeadId, po.getOutboundId(), user.getUserId(), user.getName(), OutboundConstant.RECORD_ADD, OutboundConstant.MATERIAL_STATUS_STORAGE));
        return ResultVO.ok();
    }
}
