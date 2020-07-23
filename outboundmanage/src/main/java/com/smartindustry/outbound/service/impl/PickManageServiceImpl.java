package com.smartindustry.outbound.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.smartindustry.common.bo.om.LabelRecommendBO;
import com.smartindustry.common.bo.om.MaterialBO;
import com.smartindustry.common.bo.om.PickBodyBO;
import com.smartindustry.common.bo.om.PickHeadBO;
import com.smartindustry.common.bo.si.PrintLabelBO;
import com.smartindustry.common.mapper.om.LabelRecommendMapper;
import com.smartindustry.common.mapper.om.OutboundMapper;
import com.smartindustry.common.mapper.om.PickBodyMapper;
import com.smartindustry.common.mapper.om.PickHeadMapper;
import com.smartindustry.common.mapper.si.PrintLabelMapper;
import com.smartindustry.common.mapper.si.StorageLabelMapper;
import com.smartindustry.common.pojo.om.OutboundPO;
import com.smartindustry.common.pojo.om.PickBodyPO;
import com.smartindustry.common.pojo.om.PickHeadPO;
import com.smartindustry.common.pojo.om.PickLabelPO;
import com.smartindustry.common.pojo.si.PrintLabelPO;
import com.smartindustry.common.pojo.si.StorageLabelPO;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.outbound.constant.OutboundConstant;
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
    private LabelRecommendMapper labelRecommendMapper;
    @Autowired
    private StorageLabelMapper storageLabelMapper;
    @Autowired
    private PrintLabelMapper printLabelMapper;
    @Autowired
    private OutboundMapper outboundMapper;
    @Override
    public ResultVO pageQueryPickHead(int pageNum, int pageSize, Map<String, Object> reqMap) {
        Page<PickHeadPO> page = PageHelper.startPage(pageNum, pageSize);
        List<PickHeadPO> vos = pickHeadMapper.pageQueryPickHeadMsg(reqMap);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), PickHeadVO.convert(vos)));
    }

    @Override
    public ResultVO queryByPhId(Long pickHeadId) {
        PickHeadPO po = pickHeadMapper.selectByPrimaryKey(pickHeadId);
        return ResultVO.ok().setData(PickHeadVO.convert(po));
    }

    @Override
    public ResultVO materialLoss(Map<String, Object> reqMap) {
        List<MaterialBO> bos = pickHeadMapper.materialLoss(reqMap);
        return ResultVO.ok().setData(LackMaterialVO.convert(bos));
    }

    @Override
    public ResultVO queryExItems(Long pickHeadId){
        // 若扫描了未推荐的PID,则异常列表只显示，扫描了其他推荐的PID
        List<PickHeadBO> noRecommend = pickHeadMapper.queryNoRecommend(pickHeadId);
        if(null == noRecommend || noRecommend.size() ==0 ){
            // 目前未有异常信息
            return new ResultVO(2030);
        }
        // 若已拣货量大于需求量时，将未扫描优先推荐的pid以及扫描了其他推荐的pid
        //(1) 先查询出所有的推荐的pid
        List<PickHeadBO> reList = pickHeadMapper.queryRecommend(pickHeadId);
        if(null == reList || reList.size() ==0 ){
            // 当前工单拣货单没有推荐的pid
            return new ResultVO(2031);
        }
        Map<String, String> map = reList.stream()
                .collect(HashMap::new, (m,v)->m.put(v.getMaterialNo(), v.getRecommendPid()), HashMap::putAll);
        //(2) 再查询出目前工单已经使用的推荐pid,这里必须是  拣货量大于 需求量才查询
        List<PickHeadBO> useList = pickHeadMapper.queryAllRePid(pickHeadId);
        if(null != useList || useList.size() !=0 ){
            // 当需求量满足时，没有使用任何推荐pid
            for (PickHeadBO bo:useList) {
                String materialNo = bo.getMaterialNo();
                boolean containsKey = map.containsKey(materialNo);
                if (containsKey){
                    List<String> allPidList = Arrays.asList(map.get(materialNo).split(","));
                    List<String> rePidList = Arrays.asList(bo.getRecommendPid().split(","));
                    bo.setRecommendPid(StringUtils.join(allPidList.stream()
                            .filter(s -> !rePidList.contains(s))
                            .collect(Collectors.toList()), ","));
                }
            }
            //(3) 推荐未使用的进行相减，以及拼接其他未推荐的pid
            Map<String, String> useMap = useList.stream().collect(Collectors.toMap(PickHeadBO::getMaterialNo,PickHeadBO::getRecommendPid));
            // 将未使用推荐和已使用未推荐进行组合
            for (PickHeadBO bo:noRecommend) {
                String materialNo = bo.getMaterialNo();
                boolean useKey = useMap.containsKey(materialNo);
                if (useKey){
                    bo.setRecommendPid(useMap.get(materialNo));
                }
                String exception = pickBodyMapper.queryException(pickHeadId,materialNo);
                bo.setAberrantDesc(exception);
            }
        }
        return ResultVO.ok().setData(AberrantItemsVO.convert(noRecommend));
    }

    @Override
    public ResultVO detail(Long phId) {
        PickHeadPO headPO = pickHeadMapper.selectByPrimaryKey(phId);
        if (null == headPO) {
            return new ResultVO(1002);
        }

        PickDetailVO vo = new PickDetailVO();
        vo.setPickHeadVO(headPO);

        List<PickBodyBO> bodyBOs = pickBodyMapper.queryByHeadId(phId);
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
            List<StorageLabelPO> notRecommendPOs = storageLabelMapper.queryNotRecommend(headPO.getOrderNo(), bo.getMaterialNo());
            Set<String> notRecommendVO = new HashSet<>();
            for (StorageLabelPO notRecommendPO : notRecommendPOs) {
                notRecommendVO.add(notRecommendPO.getLocationNo());
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
        if (bo == null){
            // 提示没有这个PID号
            return new ResultVO(2040);
        }
        //2 若当前输入的PID已经扫码入库，则提示不需要重复扫码
        Integer resultPid = pickHeadMapper.judgeIsPidHave(pickHeadId,packageId);
        if (resultPid != null) {
            return new ResultVO(2010);
        }
        //若输入的PID并不属于该工单对应采购单的物料范围，则提示该物料并不属于该工单
        String materialNo = bo.getMaterialNo();
        Integer resultMa = pickHeadMapper.judgeIsMaHave(pickHeadId,materialNo);
        if (resultMa == null) {
            return new ResultVO(2011);
        }
        // 判断当前物料不在拣货清单中，则提示 该物料并不在出库清单中
        List<String> maList = pickHeadMapper.judgeMaterial(pickHeadId);
        boolean flag = maList.contains(bo.getMaterialNo());
        if (!flag) {
            return new ResultVO(2012);
        }
        //2.将拣货单表体表中的已拣量作加操作
        int addResult = pickHeadMapper.addPickNum(bo.getMaterialNo(), bo.getNum());
        //3.查看扫码的PID是否在推荐的库位标签表中是否存在推荐的PID,存在则更新拣货标签表中的是否推荐标志位
        List<String> reList = pickHeadMapper.queryReOnlyPid(pickHeadId);
        boolean flagOne = reList.contains(packageId);
        Byte recommend = flagOne ? (byte)1 : (byte)2;

        PickLabelPO pickLabelPo = new PickLabelPO();
        pickLabelPo.setPickHeadId(pickHeadId);
        pickLabelPo.setPrintLabelId(bo.getPrintLabelId());
        pickLabelPo.setRecommend(recommend);
        pickLabelPo.setCreateTime(new Date());
        int insertResult = pickHeadMapper.insertPickLabel(pickLabelPo);


        int flagTwo = pickHeadMapper.judgeIsPick(pickHeadId);
        int result = (flagTwo==1) ? pickHeadMapper.updateStatus(pickHeadId, OutboundConstant.MATERIAL_STATUS_PICK) : 0;

        return ResultVO.ok().setData(ScanOutVO.convert(bo));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO packageIdDiv(Long printLabelId,Integer num){
        //(2) 将扫描的pid的dr值设为2，并且按照分料数量分成两个pid
        PrintLabelPO po = printLabelMapper.selectByPrimaryKey(printLabelId);
        PrintLabelPO poDivOne = new PrintLabelPO();
        PrintLabelPO poDivTwo = new PrintLabelPO();
        BeanUtils.copyProperties(po,poDivOne,new String[]{"printLabelId","dr"});
        BeanUtils.copyProperties(po,poDivTwo,new String[]{"printLabelId","dr"});
        // 生成分料pid1
        Date divOneTime = new Date();
        poDivOne.setCreateTime(divOneTime);
        int curNumOne = OmNoUtil.getLabelNum(printLabelMapper, null, divOneTime);
        poDivOne.setPackageId(OmNoUtil.genLabelNo(null, divOneTime, ++curNumOne));
        poDivOne.setRelatePackageId(po.getPackageId());
        poDivOne.setNum(num);
        poDivOne.setDr((byte)1);
        int resultDivOne = printLabelMapper.insert(poDivOne);

        // 生成分料pid2
        Date divTwoTime = new Date();
        poDivOne.setCreateTime(divTwoTime);
        int curNumTwo = OmNoUtil.getLabelNum(printLabelMapper, null, divTwoTime);
        poDivTwo.setPackageId(OmNoUtil.genLabelNo(null, divTwoTime, ++curNumTwo));
        poDivTwo.setRelatePackageId(po.getPackageId());
        poDivTwo.setNum(po.getNum()-num);
        poDivTwo.setDr((byte)1);
        int resultDivTwo = printLabelMapper.insert(poDivTwo);
        po.setDr((byte)2);
        int result = printLabelMapper.updateByPrimaryKey(po);
        return ResultVO.ok();
    }

    @Override
    public ResultVO showMsgByPid(String packageId){
        PrintLabelBO bo = pickHeadMapper.pickPid(packageId);
        if (bo == null){
            // PID不存在
            return new ResultVO(2040);
        }
        return ResultVO.ok().setData(ScanOutVO.convert(bo));
    }

    @Override
    public ResultVO showScanItems(int pageNum, int pageSize,Long pickHeadId){
        Page<PrintLabelBO> page = PageHelper.startPage(pageNum, pageSize);
        List<PrintLabelBO> bos = pickHeadMapper.showScanItems(pickHeadId);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), ScanOutVO.convert(bos)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO deleteScanPid(Long pickHeadId, Long printLabelId){
        int result = pickHeadMapper.deleteScanPid(pickHeadId,printLabelId);
        int flag = pickHeadMapper.judgeIsPick(pickHeadId);
        int resultStatus = (flag==1) ? pickHeadMapper.updateStatus(pickHeadId, OutboundConstant.MATERIAL_STATUS_PICK) : 0;
        return ResultVO.ok();
    }

    @Override
    public ResultVO printLabelSplit(String packageId){
        List<PrintLabelBO> bos = pickHeadMapper.printLabelSplit(packageId);
        return ResultVO.ok().setData(PrintSplitVO.convert(bos));
    }

    @Override
    public ResultVO judgeStatus(Long pickHeadId){
        // 当前工单拣货单id所关联的拣货标签表拥有数据,那么正处于物料拣货状态
        int flag = pickHeadMapper.judgeIsPick(pickHeadId);
        int result = (flag==1) ? pickHeadMapper.updateStatus(pickHeadId, OutboundConstant.MATERIAL_STATUS_PICK) : 0;
        return ResultVO.ok();
    }


    @Override
    public ResultVO updateException(Long pickHeadId,String materialNo,String exception){
        int result = pickBodyMapper.updateException(pickHeadId,materialNo,exception);
        return ResultVO.ok();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO outBoundItems(Long pickHeadId){
        //1 当形成出库单，由于物料欠缺，异常数据，则由物料拣货10变成工单审核15
        int statusCode = 0;
        Integer resultEx = pickHeadMapper.judgeIsEx(pickHeadId);
        Integer resultLack = pickHeadMapper.judgeIsLack(pickHeadId);
        if (resultEx==1 || resultLack==1){
            int resultUp = pickHeadMapper.updateStatus(pickHeadId, OutboundConstant.MATERIAL_STATUS_CHECK);
            statusCode = 1;
        }else {
            int resultUp = pickHeadMapper.updateStatus(pickHeadId, OutboundConstant.MATERIAL_STATUS_STORAGE);
            OutboundPO po = new OutboundPO();
            po.setPickHeadId(pickHeadId);
            Date date = new Date();
            po.setOutboundNo(OmNoUtil.getOutboundNo(outboundMapper, OmNoUtil.OUTBOUND, date));
            po.setStatus((byte)3);
            po.setCreateTime(date);
            po.setDr((byte)1);
            int result = outboundMapper.insert(po);
        }
        //2 当形成出库单，在不欠料的情况下，则由物料拣货10变成物料出库30
        return ResultVO.ok().setData(statusCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO deleteSplit(String packageId){
        int resultDe = pickHeadMapper.deletePid(packageId);
        int resultRe = pickHeadMapper.resumePid(packageId);
        return ResultVO.ok();
    }
}
