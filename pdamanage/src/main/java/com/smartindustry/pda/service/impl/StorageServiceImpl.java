package com.smartindustry.pda.service.impl;

import com.smartindustry.common.bo.si.LocationBO;
import com.smartindustry.common.bo.sm.StorageBodyBO;
import com.smartindustry.common.bo.sm.StorageHeadBO;
import com.smartindustry.common.mapper.si.ForkliftMapper;
import com.smartindustry.common.mapper.si.LocationMapper;
import com.smartindustry.common.mapper.si.MaterialMapper;
import com.smartindustry.common.mapper.sm.*;
import com.smartindustry.common.mapper.wo.PackageMapper;
import com.smartindustry.common.mapper.wo.ProduceOrderMapper;
import com.smartindustry.common.pojo.si.ForkliftPO;
import com.smartindustry.common.pojo.si.LocationPO;
import com.smartindustry.common.pojo.si.MaterialPO;
import com.smartindustry.common.pojo.sm.*;
import com.smartindustry.common.pojo.wo.PackagePO;
import com.smartindustry.common.pojo.wo.ProduceOrderPO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.pda.constant.CommonConstant;
import com.smartindustry.pda.constant.StorageConstant;
import com.smartindustry.pda.dto.OperateDTO;
import com.smartindustry.pda.dto.StorageDTO;
import com.smartindustry.pda.dto.StoragePreDTO;
import com.smartindustry.pda.service.IStorageService;
import com.smartindustry.pda.socket.WebSocketServer;
import com.smartindustry.pda.socket.WebSocketVO;
import com.smartindustry.pda.util.StorageNoUtil;
import com.smartindustry.pda.vo.MaterialVO;
import com.smartindustry.pda.vo.SpareMaterialVO;
import com.smartindustry.pda.vo.StorageDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 14:34 2020/11/1
 * @ Description：成品入库实体类
 * @ Modified By：
 * @ Version:     1.0
 */
@Service
public class StorageServiceImpl implements IStorageService {
    @Autowired
    private StorageHeadMapper storageHeadMapper;
    @Autowired
    private StorageBodyMapper storageBodyMapper;
    @Autowired
    private StorageRecordMapper storageRecordMapper;
    @Autowired
    private ProduceOrderMapper produceOderMapper;
    @Autowired
    private ForkliftMapper forkliftMapper;
    @Autowired
    private PackageMapper packageMapper;
    @Autowired
    StorageDetailMapper storageDetailMapper;
    @Autowired
    private LocationMapper locationMapper;
    @Autowired
    private StorageForkliftMapper storageForkliftMapper;
    @Autowired
    private MaterialMapper materialMapper;

    /**
     * @Description 生成入库单
     * @Param
     * @Return
     * @Author AnHongxu.
     * @Date 2020/11/1
     * @Time 14:44
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO generateStockbill(OperateDTO dto) {
        /*查询成品工单详细信息生成入库单表头和标体*/
        //查询成品工单详细信息
        ProduceOrderPO produceOrderPO = produceOderMapper.selectByPrimaryKey(dto.getPoid());
        if (produceOrderPO == null) {
            //没有该生产工单
            return new ResultVO(1001);
        }
        // 成品入库单表头
        StorageHeadPO storageHeadPO = new StorageHeadPO();
        storageHeadPO.setStorageNo(StorageNoUtil.genStorageHeadNo(storageHeadMapper, StorageNoUtil.RECEIPT_HEAD_YP, new Date()));
        storageHeadPO.setSourceNo(produceOrderPO.getProduceNo());
        //来源类型：2.成品入库
        storageHeadPO.setSourceType((byte) 2);
        storageHeadPO.setExpectNum(BigDecimal.valueOf(produceOrderPO.getProduceNum() * StorageConstant.F2Z));
        //入库状态：3.待入库
        storageHeadPO.setStatus(StorageConstant.STATUS_PRESTORED);
        storageHeadPO.setCreateTime(new Date());
        storageHeadPO.setDr((byte) 1);
        //新建成品入库单表头
        storageHeadMapper.insert(storageHeadPO);

        // 如果只有一个产品只生成一个入库单表体，如果有两个就生成两个
        if (produceOrderPO.getMaterialId2() == null || "".equals(produceOrderPO.getMaterialId2())) {
            // 只生成一个入库单表体
            StorageBodyPO storageBodyPO = new StorageBodyPO();
            storageBodyPO.setStorageHeadId(storageHeadPO.getStorageHeadId());
            storageBodyPO.setMaterialId(produceOrderPO.getMaterialId1());
            storageBodyPO.setCreateTime(new Date());
            storageBodyPO.setDr((byte) 1);
            storageBodyMapper.insert(storageBodyPO);
        } else {
            List<StorageBodyPO> storageBodyPOS = new ArrayList<>(2);
            StorageBodyPO storageBodyPO1 = new StorageBodyPO();
            storageBodyPO1.setStorageHeadId(storageHeadPO.getStorageHeadId());
            storageBodyPO1.setMaterialId(produceOrderPO.getMaterialId1());
            storageBodyPO1.setCreateTime(new Date());
            storageBodyPO1.setDr((byte) 1);
            storageBodyPOS.add(storageBodyPO1);

            StorageBodyPO storageBodyPO2 = new StorageBodyPO();
            storageBodyPO2.setStorageHeadId(storageHeadPO.getStorageHeadId());
            storageBodyPO2.setMaterialId(produceOrderPO.getMaterialId2());
            storageBodyPO2.setCreateTime(new Date());
            storageBodyPO2.setDr((byte) 1);
            storageBodyPOS.add(storageBodyPO2);

            storageBodyMapper.batchInsert(storageBodyPOS);
        }
        //入库操作记录:新建入库单
        storageRecordMapper.insert(new StorageRecordPO(storageHeadPO.getStorageHeadId(), 1L, StorageConstant.OPERATE_NAME_INSERT));
        return ResultVO.ok();
    }

    /**
     * @Description rfid和入库单id绑定
     * @Param
     * @Return
     * @Author AnHongxu.
     * @Date 2020/11/4
     * @Time 19:44
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO rfidBound(OperateDTO dto) {
        /*查询成品工单详细信息生成入库详细记录，进而绑定rfid*/
        //查询打包信息
        PackagePO packagePO = packageMapper.selectByPrimaryKey(dto.getPid());
        if (null == packagePO || null == packagePO.getProduceOrderId()) {
            //没有打包记录或没有绑定生产单
            return new ResultVO(1001);
        }
        //查询成品工单详细信息
        ProduceOrderPO produceOrderPO = produceOderMapper.selectByPrimaryKey(packagePO.getProduceOrderId());
        if (produceOrderPO == null) {
            //没有该生产工单
            return new ResultVO(1002);
        }
        //通过工单编号（来源单号）查入库单id
        StorageHeadPO storageHeadPO = storageHeadMapper.queryBySourceNo(produceOrderPO.getProduceNo());
        //在详细表中进行绑定
        StorageDetailPO storageDetailPO = new StorageDetailPO();
        storageDetailPO.setStorageHeadId(storageHeadPO.getStorageHeadId());
        storageDetailPO.setRfid(packagePO.getRfid());
        storageDetailPO.setStorageStatus(StorageConstant.STATUS_PRESTORED);
        storageDetailMapper.insertSelective(storageDetailPO);
        return ResultVO.ok();
    }

    /**
     * 详情
     *
     * @param session
     * @param dto
     * @return
     */
    @Override
    public ResultVO detail(HttpSession session, StorageDTO dto) {
        if (null == dto.getShid()) {
            //没有有传入入库单表头
            return new ResultVO(1001);
        }
        // 入库信息
        StorageHeadBO storageHeadBO = storageHeadMapper.queryPdaDetailByShid(dto.getShid());
        if (null == storageHeadBO) {
            //没有该入库单
            return new ResultVO(1002);
        }
        StorageDetailVO vo = StorageDetailVO.convert(storageHeadBO);

        // 储位图
        Map<Long, StorageDetailVO.LocationVO> lvos = new HashMap<>();
        for (StorageBodyBO bo : storageHeadBO.getBos()) {
            StorageDetailVO.LocationVO lvo = new StorageDetailVO.LocationVO();
            lvo.setColor(StorageDetailVO.COLORS[lvos.size()]);
            lvo.setMinfo(bo.getMaterialName() + " " + bo.getMaterialModel());
            lvos.put(bo.getMaterialId(), lvo);
        }
        List<LocationPO> locationPOs = locationMapper.queryByMids(new ArrayList<>(lvos.keySet()));
        for (LocationPO locationPO : locationPOs) {
            StorageDetailVO.LocationVO lvo = lvos.get(locationPO.getMaterialId());
            //如果库位未满，返回推荐，满了则不推荐
            if (!judgeFull(locationPO.getLocationId())) {
                lvo.getLrfids().add(locationPO.getLocationNo());
            }
        }
        vo.setLvos(new ArrayList<>(lvos.values()));

        // 叉车信息
        List<ForkliftPO> pos = forkliftMapper.queryByShid(dto.getShid());
        if (null != pos && pos.size() > 0) {
            List<String> fnames = new ArrayList<>(pos.size());
            for (ForkliftPO po : pos) {
                fnames.add(po.getForkliftNo());
            }
            vo.setFnames(fnames);
        }
        BigDecimal storageNum = storageHeadBO.getStorageNum() == null ? BigDecimal.valueOf(0) : storageHeadBO.getStorageNum();
        vo.setCnum(storageNum.add(BigDecimal.valueOf(null == pos ? 0 : pos.size())));
        session.setAttribute(CommonConstant.SESSION_SHID, dto.getShid());
        return ResultVO.ok().setData(vo);
    }

    /**
     * 查看当前储位是否已满
     *
     * @param lid
     * @return
     */
    public boolean judgeFull(Long lid) {
        // 获取当前储位的在库数量
        BigDecimal curLnoNum = storageDetailMapper.queryStored(lid);
        BigDecimal trayNum = locationMapper.selectByPrimaryKey(lid).getHoldTrayNum();
        if (curLnoNum.compareTo(trayNum) == -1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 叉车插入原产区货物接口
     *
     * @param session
     * @param dto
     * @return
     */
    @Override
    public ResultVO execute(HttpSession session, StorageDTO dto) {
        String imei = (String) session.getAttribute(CommonConstant.SESSION_IMEI);
        if (null == imei) {
            //为获取到session中的imei
            return new ResultVO(1001);
        }

        //通过rfid获取入库单和物料信息
        StorageDetailPO storageDetailPO = storageDetailMapper.queryByRfid(dto.getMrfid());
        StorageHeadPO storageHeadPO = storageHeadMapper.selectByPrimaryKey(storageDetailPO.getStorageHeadId());
        if (null == storageHeadPO) {
            //没有该入库单
            return new ResultVO(1002);
        }

        /*将当前叉车放入入库单叉车表中，加入执行任务*/
        //查询叉车表信息
        ForkliftPO forkliftPO = forkliftMapper.queryByImei(imei);
        StorageForkliftPO storageForkliftPO = new StorageForkliftPO();
        storageForkliftPO.setRfid(dto.getMrfid());
        storageForkliftPO.setForkliftId(forkliftPO.getForkliftId());
        storageForkliftPO.setStorageHeadId(storageHeadPO.getStorageHeadId());
        storageForkliftMapper.insertSelective(storageForkliftPO);

        //查找物料信息
        StorageHeadBO storageHeadBO = storageHeadMapper.queryPdaDetailByShid(storageHeadPO.getStorageHeadId());
        if (null == storageHeadBO) {
            //该入库单没有物料信息
            return new ResultVO(1003);
        }

        StorageDetailVO vo = StorageDetailVO.convert(storageHeadBO);
        // 储位图
        Map<Long, StorageDetailVO.LocationVO> lvos = new HashMap<>();
        for (StorageBodyBO bo : storageHeadBO.getBos()) {
            StorageDetailVO.LocationVO lvo = new StorageDetailVO.LocationVO();
            lvo.setColor(StorageDetailVO.COLORS[lvos.size()]);
            lvo.setMinfo(bo.getMaterialName() + " " + bo.getMaterialModel());
            lvos.put(bo.getMaterialId(), lvo);
        }
        List<LocationPO> locationPOs = locationMapper.queryByMids(new ArrayList<>(lvos.keySet()));
        for (LocationPO locationPO : locationPOs) {
            StorageDetailVO.LocationVO lvo = lvos.get(locationPO.getMaterialId());
            lvo.getLrfids().add(locationPO.getLocationNo());
        }
        vo.setLvos(new ArrayList<>(lvos.values()));

        // 叉车信息
        List<ForkliftPO> pos = forkliftMapper.queryByShid(dto.getShid());
        if (null != pos && pos.size() > 0) {
            List<String> fnames = new ArrayList<>(pos.size());
            for (ForkliftPO po : pos) {
                fnames.add(po.getForkliftNo());
            }
            vo.setFnames(fnames);
        }
        BigDecimal storageNum = storageHeadBO.getStorageNum() == null ? BigDecimal.valueOf(0) : storageHeadBO.getStorageNum();
        vo.setCnum(storageNum.add(BigDecimal.valueOf(null == pos ? 0 : pos.size())));
        session.setAttribute(CommonConstant.SESSION_SHID, storageHeadPO.getStorageHeadId());

        //添加该入库单任务的操作记录表
        //入库操作记录:执行入库或参与入库
        if (vo.getCnum().compareTo(new BigDecimal(1)) == 0) {
            storageRecordMapper.insert(new StorageRecordPO(storageHeadPO.getStorageHeadId(), forkliftPO.getForkliftId(), StorageConstant.OPERATE_NAME_EXECUTE));
        } else {
            storageRecordMapper.insert(new StorageRecordPO(storageHeadPO.getStorageHeadId(), forkliftPO.getForkliftId(), StorageConstant.OPERATE_NAME_JOIN));
        }
        return ResultVO.ok().setData(vo);
    }

    /**
     * 叉车插入备货区货物接口
     *
     * @param session
     * @param dto
     * @return
     */
    @Override
    public ResultVO executeForPre(HttpSession session, StorageDTO dto) {
        String imei = (String) session.getAttribute(CommonConstant.SESSION_IMEI);
        if (null == imei) {
            //为获取到session中的imei
            return new ResultVO(1001);
        }

        //通过rfid获取入库单和物料信息
        StorageDetailPO storageDetailPO = storageDetailMapper.queryByLidAndRfid(dto.getMrfid());
        if (null == storageDetailPO) {
            //在详细表中查不到备料区数据
            return new ResultVO(1002);
        }
        //根据成品id查询成品信息
        MaterialPO materialPO = materialMapper.selectByPrimaryKey(storageDetailPO.getMaterialId());
        if (null == materialPO) {
            //查不到成品基本信息
            return new ResultVO(1003);
        }
        //构造详情格式的StorageHeadBO
        StorageHeadBO storageHeadBO = new StorageHeadBO();
        StorageBodyBO storageBodyBO = new StorageBodyBO();
        storageBodyBO.setMaterialName(materialPO.getMaterialName());
        storageBodyBO.setMaterialModel(materialPO.getMaterialModel());
        //等级level代写

        storageBodyBO.setPackageVolume(materialPO.getPackageVolume());

        List<StorageBodyBO> bodyBOList = new ArrayList<>(1);
        bodyBOList.add(storageBodyBO);
        storageHeadBO.setBos(bodyBOList);

        StorageDetailVO vo = StorageDetailVO.convert(storageHeadBO);
        // 储位图
        Map<Long, StorageDetailVO.LocationVO> lvos = new HashMap<>();
        for (StorageBodyBO bo : storageHeadBO.getBos()) {
            StorageDetailVO.LocationVO lvo = new StorageDetailVO.LocationVO();
            lvo.setColor(StorageDetailVO.COLORS[lvos.size()]);
            lvo.setMinfo(bo.getMaterialName() + " " + bo.getMaterialModel());
            lvos.put(bo.getMaterialId(), lvo);
        }
        List<LocationPO> locationPOs = locationMapper.queryByMids(new ArrayList<>(lvos.keySet()));
        for (LocationPO locationPO : locationPOs) {
            StorageDetailVO.LocationVO lvo = lvos.get(locationPO.getMaterialId());
            lvo.getLrfids().add(locationPO.getLocationNo());
        }
        vo.setLvos(new ArrayList<>(lvos.values()));

        //当前叉车执行信息
        ForkliftPO forkliftPO = forkliftMapper.queryByImei(imei);
        List<String> fnames = new ArrayList<>(1);
        fnames.add(forkliftPO.getForkliftName());
        vo.setFnames(fnames);
        return ResultVO.ok().setData(vo);
    }

    /**
     * @Description 进入备料区存储时选择物料弹窗
     * @Param
     * @Return
     * @Author AnHongxu.
     * @Date 2020/11/6
     * @Time 9:39
     */
    @Override
    public ResultVO chooseMaterialShow(HttpSession session, StorageDTO dto) {
        // 根据栈板rfid查询入库单
        StorageDetailPO storageDetailPO = storageDetailMapper.queryByRfid(dto.getMrfid());
        StorageHeadPO storageHeadPO = storageHeadMapper.selectByPrimaryKey(storageDetailPO.getStorageHeadId());
        List<MaterialPO> pos = storageBodyMapper.queryMaterial(storageHeadPO.getStorageHeadId());
        sendChooseMaterialShowMsg(pos);
        return ResultVO.ok();
    }

    /**
     * @Description 进入备料区司机选择产品后触发动作
     * @Param
     * @Return
     * @Author AnHongxu.
     * @Date 2020/11/6
     * @Time 15:39
     */
    @Override
    public ResultVO chooseMaterialConfirm(HttpSession session, StorageDTO dto) {
        String imei = (String) session.getAttribute(CommonConstant.SESSION_IMEI);
        if (null == imei) {
            //为获取到session中的imei
            return new ResultVO(1001);
        }
        //查询叉车id
        ForkliftPO forkliftPO = forkliftMapper.queryByImei(imei);

        //根据叉车id查询当前的rfid
        StorageForkliftPO storageForkliftPO = storageForkliftMapper.queryByFid(forkliftPO.getForkliftId());
        //通过rfid获取入库单和物料信息
        StorageDetailPO storageDetailPO = storageDetailMapper.queryByLidAndRfid(storageForkliftPO.getRfid());
        if (null == storageDetailPO) {
            //在详细表中查不到备料区数据
            return new ResultVO(1002);
        }
        // 不知道最终是去成品区还是去备料区，所以信息暂存
        storageDetailPO.setRfid(dto.getMrfid());
        storageDetailPO.setMaterialId(dto.getMid());
        storageDetailMapper.insertSelective(storageDetailPO);
        return ResultVO.ok();
    }


    /**
     * WebSocket 备料区入库选择成品类型消息
     *
     * @param materialPOS
     */
    private void sendChooseMaterialShowMsg(List<MaterialPO> materialPOS) {
        WebSocketVO vo = new WebSocketVO();
        WebSocketVO.TitleVO titleVO = new WebSocketVO.TitleVO();
        titleVO.setTip("备料区入库提示");
        titleVO.setMsg("您是否要进行备料区入库作业？如果是请选择成品类型。");
        //类型为弹窗
        titleVO.setType((byte) 4);

        //构造假数据进行测试
        MaterialPO materialPO1 = materialMapper.selectByPrimaryKey((long) 470);
        MaterialPO materialPO2 = materialMapper.selectByPrimaryKey((long) 471);
        materialPOS.add(materialPO1);
        materialPOS.add(materialPO2);

        List<WebSocketVO.MaterialVO> materialVOS = new ArrayList<>(2);
        for (MaterialPO materialPO : materialPOS) {
            WebSocketVO.MaterialVO materialVO = new WebSocketVO.MaterialVO();
            materialVO.setMid(materialPO.getMaterialId());
            materialVO.setMinfo(materialPO.getMaterialName() + materialPO.getMaterialLevel() + "  " + materialPO.getMaterialModel());
            materialVOS.add(materialVO);
        }
        titleVO.setMvos(materialVOS);
        vo.setTitle(titleVO);
        WebSocketServer.sendAllMsg(vo);
    }


    /**
     * @Description 进备料区插货物的弹窗
     * @Param
     * @Return
     * @Author AnHongxu.
     * @Date 2020/11/6
     * @Time 16:05
     */
    public ResultVO executeSpareAreaShow(StoragePreDTO dto) {
        // 根据栈板rfid查询物料信息
        StorageDetailPO storageDetailPO = storageDetailMapper.queryByRfid(dto.getMrfid());
        if (null == storageDetailPO) {
            // 没有该物料信息
            return new ResultVO(1007);
        }
        MaterialPO materialPO = materialMapper.selectByPrimaryKey(storageDetailPO.getMaterialId());
        //发送socket消息
        sendexecuteSpareAreaShowMsg(dto.getMrfid(), materialPO);

        SpareMaterialVO vo = new SpareMaterialVO();
        if (null == storageDetailPO.getStorageHeadId()) {
            vo.setFlag(true);
            vo.setRfid(dto.getMrfid());
            vo.setMmodel(materialPO.getMaterialModel());
            vo.setMname(materialPO.getMaterialName());
        } else {
            vo.setFlag(false);
        }
        return ResultVO.ok().setData(vo);
    }


    /**
     * WebSocket 备料区入库选择成品类型消息
     *
     * @param materialPO
     */
    private void sendexecuteSpareAreaShowMsg(String mrfid, MaterialPO materialPO) {
        WebSocketVO vo = new WebSocketVO();
        WebSocketVO.TitleVO titleVO = new WebSocketVO.TitleVO();
        titleVO.setTip("备料区物料信息");
        titleVO.setMsg("您目前操作的为备料区成品，是否执行备料区成品入库仓库？");
        //类型为弹窗
        titleVO.setType((byte) 4);
        WebSocketVO.MaterialVO materialVO = new WebSocketVO.MaterialVO();
        materialVO.setMid(materialPO.getMaterialId());
//        materialVO.setMname(materialPO.getMaterialName());
//        materialVO.setMlevel(materialPO.getMaterialLevel());
//        materialVO.setModel(materialPO.getMaterialModel());
        vo.setTitle(titleVO);
        WebSocketServer.sendAllMsg(vo);
    }


    /**
     * @Description 原产区入库到成品区
     * @Param
     * @Return
     * @Author AnHongxu.
     * @Date 2020/11/7
     * @Time 16:41
     */
    public ResultVO finishedOriginToStorage(HttpSession session, StoragePreDTO dto) {
        // 当前叉车信息
        String imei = (String) session.getAttribute(CommonConstant.SESSION_IMEI);
        // 根据imei查询出叉车id
        ForkliftPO forkliftPO = forkliftMapper.queryByImei(imei);
        // 根据栈板rfid查询入库单
        StorageDetailPO storageDetailPO = storageDetailMapper.queryByRfid(dto.getMrfid());
        StorageHeadPO storageHeadPO = storageHeadMapper.selectByPrimaryKey(storageDetailPO.getStorageHeadId());
        // 查询当前储位的基本信息
        LocationBO locationBO = locationMapper.queryByRfid(dto.getLrfid());
        //# 当前储位为成品区并且来源订单类型是生产入库
        if (locationBO.getLocationTypeId().equals(StorageConstant.TYPE_FINISHED_AREA) && storageHeadPO.getSourceType().equals(StorageConstant.TYPE_PRODUCT_STORAGE)) {
            //1. 入库详情表更新添加信息
            storageDetailPO.setLocationId(locationBO.getLocationId());
            storageDetailPO.setStorageTime(new Date());
            storageDetailPO.setMaterialId(locationBO.getMaterialId());
            storageDetailPO.setStorageStatus(StorageConstant.STATUS_STORED);
            storageDetailPO.setPreparation(StorageConstant.Preparation_NO);
            storageDetailMapper.updateByPrimaryKey(storageDetailPO);
            //2.入库单表头已入库数量+1,以及判断入库单状态
            storageHeadPO.setStorageNum(storageHeadPO.getStorageNum().add(new BigDecimal(1)));
            storageHeadPO.setWarehouseId(locationBO.getWarehouseId());
            //更新入库单的状态
            if (storageHeadPO.getStorageNum().add(new BigDecimal(1)).compareTo(storageHeadPO.getExpectNum()) == -1) {
                storageHeadPO.setStatus(StorageConstant.STATUS_STOREING);
            } else {
                storageHeadPO.setStatus(StorageConstant.STATUS_STORED);
                storageHeadPO.setStorageTime(new Date());


                //插入入库完成操作记录
                storageRecordMapper.insert(new StorageRecordPO(storageHeadPO.getStorageHeadId(), forkliftPO.getForkliftId(), StorageConstant.OPERATE_NAME_FINISH));
            }
            storageHeadMapper.updateByPrimaryKey(storageHeadPO);
            //3. 入库单表体已入库数量+1
            // 根据入库单表头id和物料id唯一查找入库单表体，进行数量+1
            StorageBodyPO storageBodyPO = storageBodyMapper.queryByShidAndMid(storageHeadPO.getStorageHeadId(), locationBO.getMaterialId());
            if (null == storageBodyPO) {
                // 没有该body体
                return new ResultVO(1002, "没有该body体");
            }
            storageBodyPO.setStorageNum(storageBodyPO.getStorageNum().add(new BigDecimal(1)));
            storageBodyMapper.updateByPrimaryKeySelective(storageBodyPO);
            //4.释放叉车
            //根据叉车id查询当前执行入库的入库叉车表记录,删除
            StorageForkliftPO storageForkliftPO = storageForkliftMapper.queryByFid(forkliftPO.getForkliftId());
            if (storageForkliftPO != null) {
                storageForkliftMapper.deleteByPrimaryKey(storageForkliftPO.getStorageForkliftId());
            }
        }
        //返回Socket视图
        WebSocketVO vo = new WebSocketVO();
        vo.setIsShow(true);
        WebSocketServer.sendAllMsg(vo);
        return ResultVO.ok();
    }


    /**
     * @Description 原产区入库到备料区
     * @Param
     * @Return
     * @Author AnHongxu.
     * @Date 2020/11/7
     * @Time 16:40
     */
    public ResultVO finishedOriginToSpareArea(HttpSession session, StoragePreDTO dto) {
        // 当前叉车信息
        String imei = (String) session.getAttribute(CommonConstant.SESSION_IMEI);
        // 根据imei查询出叉车id
        ForkliftPO forkliftPO = forkliftMapper.queryByImei(imei);
        // 根据栈板rfid查询入库单
        StorageDetailPO storageDetailPO = storageDetailMapper.queryByRfid(dto.getMrfid());
        StorageHeadPO storageHeadPO = storageHeadMapper.selectByPrimaryKey(storageDetailPO.getStorageHeadId());
        // 查询当前储位的基本信息
        LocationBO locationBO = locationMapper.queryByRfid(dto.getLrfid());
        //# 叉车运送到备货区,rfid 和 入库单解绑,也就是删除其生产来源单号
        if (locationBO.getLocationTypeId().equals(StorageConstant.TYPE_PREPARATION_AREA) && storageHeadPO.getSourceType().equals(StorageConstant.TYPE_PRODUCT_STORAGE)) {
            // 首先更新入库详情表，添加信息
            //1. 入库详情表更新添加信息
            storageDetailPO.setLocationId(locationBO.getLocationId());
            storageDetailPO.setStorageTime(new Date());
            storageDetailPO.setStorageStatus(StorageConstant.STATUS_STORED);
            storageDetailPO.setPreparation(StorageConstant.Preparation_YES);
            storageDetailMapper.updateByPrimaryKey(storageDetailPO);
            //2.入库单表头已入库数量+1,以及判断入库单状态
            storageHeadPO.setStorageNum(storageHeadPO.getStorageNum().add(new BigDecimal(1)));
            storageHeadPO.setWarehouseId(locationBO.getWarehouseId());
            //更新入库单的状态
            if (storageHeadPO.getStorageNum().add(new BigDecimal(1)).compareTo(storageHeadPO.getExpectNum()) == -1) {
                storageHeadPO.setStatus(StorageConstant.STATUS_STOREING);
            } else {
                storageHeadPO.setStatus(StorageConstant.STATUS_STORED);
                storageHeadPO.setStorageTime(new Date());
                //插入入库完成操作记录
                storageRecordMapper.insert(new StorageRecordPO(storageHeadPO.getStorageHeadId(), forkliftPO.getForkliftId(), StorageConstant.OPERATE_NAME_FINISH));
            }
            storageHeadMapper.updateByPrimaryKey(storageHeadPO);
            //3. 入库单表体已入库数量+1
            // 根据入库单表头id和物料id唯一查找入库单表体，进行数量+1
            StorageBodyPO storageBodyPO = storageBodyMapper.queryByShidAndMid(storageHeadPO.getStorageHeadId(), locationBO.getMaterialId());
            if (null == storageBodyPO) {
                // 没有该body体
                return new ResultVO(1002, "没有该body体");
            }
            storageBodyPO.setStorageNum(storageBodyPO.getStorageNum().add(new BigDecimal(1)));
            storageBodyMapper.updateByPrimaryKeySelective(storageBodyPO);
            //4.释放叉车
            //根据叉车id查询当前执行入库的入库叉车表记录,删除
            StorageForkliftPO storageForkliftPO = storageForkliftMapper.queryByFid(forkliftPO.getForkliftId());
            if (storageForkliftPO != null) {
                storageForkliftMapper.deleteByPrimaryKey(storageForkliftPO.getStorageForkliftId());
            }
        }
        //返回Socket视图
        WebSocketVO vo = new WebSocketVO();
        vo.setIsShow(true);
        WebSocketServer.sendAllMsg(vo);
        return ResultVO.ok();
    }


    /**
     * @Description 备料区入成品区
     * @Param
     * @Return
     * @Author AnHongxu.
     * @Date 2020/11/7
     * @Time 16:40
     */
    public ResultVO finishedSpareAreaToStorage(HttpSession session, StoragePreDTO dto) {
        // 当前叉车信息
        String imei = (String) session.getAttribute(CommonConstant.SESSION_IMEI);
        // 根据imei查询出叉车id
        ForkliftPO forkliftPO = forkliftMapper.queryByImei(imei);
        // 根据栈板rfid查询入库单
        StorageDetailPO storageDetailPO = storageDetailMapper.queryByRfid(dto.getMrfid());
        // 查询当前储位的基本信息
        LocationBO locationBO = locationMapper.queryByRfid(dto.getLrfid());

        //# 阅读器扫描成品区库位，并且来源是备货入库单，入库第一条成功后，此时生成备货入库单
        if (locationBO.getLocationTypeId().equals(StorageConstant.TYPE_FINISHED_AREA) && storageDetailPO.getPreparation().equals(StorageConstant.Preparation_YES)) {
            // 看是不是当前时间段备料区入库的第一单，如果是第一单，生成备料区入库单
            //根据来源类型查询是否当前有备料区入库单
            List<StorageHeadPO> storageHeadPOS = storageHeadMapper.queryByStatus(StorageConstant.STATUS_STOREING, StorageConstant.TYPE_PRE_STORAGE);
            if (storageHeadPOS.size() <= 0) {
                // 如果当前时间段没有备料入库单，那么就新增一条备料入库单
                // 生成备料区入库单表头
                StorageHeadPO storageHeadPO = new StorageHeadPO();
                storageHeadPO.setStorageNo(StorageNoUtil.genStorageHeadNo(storageHeadMapper, StorageNoUtil.RECEIPT_HEAD_YP, new Date()));
                storageHeadPO.setWarehouseId(locationBO.getWarehouseId());
                storageHeadPO.setSourceType(StorageConstant.TYPE_PRE_STORAGE);
                storageHeadPO.setStorageNum(new BigDecimal(1));
                storageHeadPO.setStatus(StorageConstant.STATUS_STOREING);
                storageHeadPO.setCreateTime(new Date());
                storageHeadPO.setDr((byte) 1);
                storageHeadMapper.insert(storageHeadPO);
                //生成一条对应的入库单表体
                // 只生成一个入库单表体
                StorageBodyPO storageBodyPO = new StorageBodyPO();
                storageBodyPO.setStorageHeadId(storageHeadPO.getStorageHeadId());
                storageBodyPO.setMaterialId(storageDetailPO.getMaterialId());
                storageBodyPO.setLocationId(locationBO.getLocationId());
                storageBodyPO.setStorageNum(new BigDecimal(1));
                storageBodyPO.setCreateTime(new Date());
                storageBodyPO.setDr((byte) 1);
                storageBodyMapper.insert(storageBodyPO);

                //更新详情记录表变为已经不在备料区了，并添加一条新的记录
                storageDetailPO.setPreparation((byte) 1);
                storageDetailMapper.updateByPrimaryKey(storageDetailPO);

                //添加入库记录
                StorageDetailPO poForStorage = new StorageDetailPO();
                poForStorage.setStorageHeadId(storageBodyPO.getStorageHeadId());
                poForStorage.setLocationId(locationBO.getLocationId());
                poForStorage.setMaterialId(storageDetailPO.getMaterialId());
                poForStorage.setStorageNum(new BigDecimal(1));
                poForStorage.setStorageTime(new Date());
                poForStorage.setRfid(dto.getMrfid());
                poForStorage.setStorageStatus(StorageConstant.STATUS_STORED);
                poForStorage.setPreparation(StorageConstant.Preparation_NO);
                storageDetailMapper.insertSelective(poForStorage);
            } else {
                //已经有入库单了，加入到有的入库单中，更新入库单入库数量，以及更新或添加表体信息
                StorageHeadPO storageHeadPO = storageHeadPOS.get(0);
                storageHeadPO.setStorageNum(storageHeadPO.getStorageNum().add(new BigDecimal(1)));
                //根据表头和物料id查看是否之前有该备料入库单的表体，如果有更新，没有进行添加
                StorageBodyPO storageBodyPO = storageBodyMapper.queryByShidAndMid(storageHeadPO.getStorageHeadId(), locationBO.getMaterialId());
                if (storageBodyPO == null) {
                    //创建新的表体
                    storageBodyPO.setStorageHeadId(storageHeadPO.getStorageHeadId());
                    storageBodyPO.setMaterialId(storageDetailPO.getMaterialId());
                    storageBodyPO.setLocationId(locationBO.getLocationId());
                    storageBodyPO.setStorageNum(new BigDecimal(1));
                    storageBodyPO.setCreateTime(new Date());
                    storageBodyPO.setDr((byte) 1);
                    storageBodyMapper.insert(storageBodyPO);
                } else {
                    //更新原来的表体
                    storageBodyPO.setStorageNum(storageBodyPO.getStorageNum());
                    storageBodyMapper.updateByPrimaryKey(storageBodyPO);
                }
                //更新详情记录表变为已经不在备料区了，并添加一条新的记录
                storageDetailPO.setPreparation((byte) 1);
                storageDetailMapper.updateByPrimaryKey(storageDetailPO);

                //添加入库记录
                StorageDetailPO poForStorage = new StorageDetailPO();
                poForStorage.setStorageHeadId(storageBodyPO.getStorageHeadId());
                poForStorage.setLocationId(locationBO.getLocationId());
                poForStorage.setMaterialId(storageDetailPO.getMaterialId());
                poForStorage.setStorageNum(new BigDecimal(1));
                poForStorage.setStorageTime(new Date());
                poForStorage.setRfid(dto.getMrfid());
                poForStorage.setStorageStatus(StorageConstant.STATUS_STORED);
                poForStorage.setPreparation(StorageConstant.Preparation_NO);
                storageDetailMapper.insertSelective(poForStorage);
            }
            //将当前运作的叉车删掉
            //根据叉车id查询当前执行入库的入库叉车表记录,删除
            StorageForkliftPO storageForkliftPO = storageForkliftMapper.queryByFid(forkliftPO.getForkliftId());
            if (storageForkliftPO != null) {
                storageForkliftMapper.deleteByPrimaryKey(storageForkliftPO.getStorageForkliftId());
            }
        }
        //返回Socket视图
        WebSocketVO vo = new WebSocketVO();
        vo.setIsShow(true);
        WebSocketServer.sendAllMsg(vo);

        return ResultVO.ok();
    }

}
