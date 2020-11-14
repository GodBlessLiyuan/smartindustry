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
import com.smartindustry.pda.PdaApplication;
import com.smartindustry.pda.config.RfidConfig;
import com.smartindustry.pda.constant.CommonConstant;
import com.smartindustry.pda.constant.StorageConstant;
import com.smartindustry.pda.dto.OperateDTO;
import com.smartindustry.pda.dto.StorageDTO;
import com.smartindustry.pda.service.IStorageService;
import com.smartindustry.pda.socket.WebSocketServer;
import com.smartindustry.pda.socket.WebSocketVO;
import com.smartindustry.pda.util.StorageNoUtil;
import com.smartindustry.pda.vo.StorageDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

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
@Slf4j
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
    @Autowired
    private RfidConfig rfidConfig;

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
        storageRecordMapper.insert(new StorageRecordPO(storageHeadPO.getStorageHeadId(), null, StorageConstant.OPERATE_NAME_INSERT));
        return ResultVO.ok();
    }

    /**
     * @Description 与mes交互 rfid和入库单id绑定
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
     * @Description 与pda交互 rfid和入库单id绑定
     * @Param
     * @Return
     * @Author AnHongxu.
     * @Date 2020/11/4
     * @Time 19:44
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO rfidBoundStorageHeadId(StorageDTO dto) {
        /*最新的未入库完成的，并且入库单来源为生产订单的入库单id，进而绑定rfid，插入到详细记录表中*/
        //新的未入库完成的，并且入库单来源为生产订单的入库单id
        StorageHeadPO storageHeadPO = storageHeadMapper.queryNowUnfinishedByType(StorageConstant.TYPE_PRODUCT_STORAGE);
        if (null == storageHeadPO) {
            //没有当前生产订单入库单信息
            return new ResultVO(1001);
        }
        //在详细表中进行绑定
        StorageDetailPO storageDetailPO = new StorageDetailPO();
        storageDetailPO.setStorageHeadId(storageHeadPO.getStorageHeadId());
        storageDetailPO.setRfid(dto.getMrfid());
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

        StorageHeadBO storageHeadBO = storageHeadMapper.queryPdaDetailByShid(dto.getShid());
        if (null == storageHeadBO) {
            //没有该入库单
            return new ResultVO(1002);
        }
        if (storageHeadBO.getSourceType().equals(StorageConstant.TYPE_PRE_STORAGE)) {
            //如果是备料区的单子只展示当前的信息
            //根据rfid查找当前物料信息
            //通过rfid获取入库单和物料信息
            StorageDetailPO storageDetailPO = storageDetailMapper.queryByRfid(dto.getMrfid());
            MaterialPO materialPO = materialMapper.selectByPrimaryKey(storageDetailPO.getMaterialId());
            StorageBodyBO storageBodyBO = new StorageBodyBO();
            storageBodyBO.setMaterialName(materialPO.getMaterialName());
            storageBodyBO.setMaterialModel(materialPO.getMaterialModel());
            storageBodyBO.setPackageVolume(materialPO.getPackageVolume());
        }
        // 入库信息
        if (null == storageHeadBO) {
            //没有该入库单
            return new ResultVO(1002);
        }
        StorageDetailVO vo = StorageDetailVO.convert(storageHeadBO);

        // 储位图
        Map<Long, StorageDetailVO.LocationVO> lvos = new HashMap<>();
        for (StorageBodyBO bo : storageHeadBO.getBos()) {
            StorageDetailVO.LocationVO lvo = new StorageDetailVO.LocationVO();
            lvo.setColor(CommonConstant.COLORS[lvos.size()]);
            lvo.setMinfo(bo.getMaterialName() + " " + bo.getMaterialModel());
            lvos.put(bo.getMaterialId(), lvo);
        }
        Map<String, String> rfidMaps = rfidConfig.parseMap(rfidConfig.getMaps());
        if (lvos.size() > 0) {
            List<LocationPO> locationPOs = locationMapper.queryRecommendByMids(new ArrayList<>(lvos.keySet()));
            for (LocationPO locationPO : locationPOs) {
                StorageDetailVO.LocationVO lvo = lvos.get(locationPO.getMaterialId());
                for (Map.Entry<String, String> entry : rfidMaps.entrySet()) {
                    if (entry.getKey().equals(locationPO.getLocationNo())) {
                        lvo.getLrfids().add(entry.getValue());
                    }
                }
            }
            vo.setLvos(new ArrayList<>(lvos.values()));
        }
        // 叉车信息
        List<ForkliftPO> pos = forkliftMapper.queryByShid(dto.getShid());
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
    public boolean judgeCanPut(Long lid) {
        // 获取当前储位的在库数量
        // BigDecimal curLnoNum = storageDetailMapper.queryStored(lid);
        BigDecimal existNum = locationMapper.selectByPrimaryKey(lid).getExistNum();
        BigDecimal trayNum = locationMapper.selectByPrimaryKey(lid).getHoldTrayNum();
        if (trayNum.compareTo(existNum) == 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 叉车插入原产区货物接口
     *
     * @param session
     * @param mrfid
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO execute(HttpSession session, String mrfid) {
        String imei = (String) session.getAttribute(CommonConstant.SESSION_IMEI);
        if (null == imei) {
            //为获取到session中的imei
            return new ResultVO(1001);
        }

        //通过rfid获取入库单和物料信息
        StorageDetailPO storageDetailPO = storageDetailMapper.queryByRfid(mrfid);
        StorageHeadPO storageHeadPO = storageHeadMapper.selectByPrimaryKey(storageDetailPO.getStorageHeadId());
        if (null == storageHeadPO) {
            //没有该入库单
            return new ResultVO(1002);
        }

        //*将当前叉车放入入库单叉车表中，加入执行任务*//*
        //查询叉车表信息
        ForkliftPO forkliftPO = forkliftMapper.queryByImei(imei);
        StorageForkliftPO storageForkliftPO = new StorageForkliftPO();
        storageForkliftPO.setRfid(mrfid);
        storageForkliftPO.setForkliftId(forkliftPO.getForkliftId());
        storageForkliftPO.setStorageHeadId(storageHeadPO.getStorageHeadId());
        storageForkliftMapper.insertSelective(storageForkliftPO);

        // 叉车状态 - 忙碌
        forkliftPO.setStatus(CommonConstant.STATUS_FORKLIFT_BUSY);
        forkliftMapper.updateByPrimaryKey(forkliftPO);

        //发送socket请求
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                //发送socket消息
                WebSocketServer.sendAllMsg(WebSocketVO.createShowVO(storageHeadPO.getStorageHeadId(), CommonConstant.FLAG_STORAGE));
            }
        });

        return ResultVO.ok();
    }


    /**
     * 叉车插入备货区货物接口
     *
     * @param session
     * @param mrfid
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO executeForPre(HttpSession session, String mrfid) {
        String imei = (String) session.getAttribute(CommonConstant.SESSION_IMEI);
        if (null == imei) {
            //为获取到session中的imei
            return new ResultVO(1001);
        }
        //查询当前执行的备料区入库单
        //首先查询出目前的入库中的备货入库单
        List<StorageHeadPO> storageHeadPOS = storageHeadMapper.queryByStatus(StorageConstant.STATUS_STOREING, StorageConstant.TYPE_PRE_STORAGE);
        if (storageHeadPOS.size() <= 0) {
            //没有备料区当前时段的入库单
            return new ResultVO(1002);
        }
        /*将当前叉车放入入库单叉车表中，加入执行任务*/
        //查询叉车表信息
        ForkliftPO forkliftPO = forkliftMapper.queryByImei(imei);
        StorageForkliftPO storageForkliftPO = new StorageForkliftPO();
        storageForkliftPO.setRfid(mrfid);
        storageForkliftPO.setForkliftId(forkliftPO.getForkliftId());
        storageForkliftPO.setStorageHeadId(storageHeadPOS.get(0).getStorageHeadId());
        storageForkliftMapper.insertSelective(storageForkliftPO);

        // 叉车状态 - 忙碌
        forkliftPO.setStatus(CommonConstant.STATUS_FORKLIFT_BUSY);
        forkliftMapper.updateByPrimaryKey(forkliftPO);

        //发送socket请求
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                //发送socket请求
                WebSocketServer.sendAllMsg(WebSocketVO.createShowVO(storageHeadPOS.get(0).getStorageHeadId(), CommonConstant.FLAG_STORAGE));
            }
        });

        return ResultVO.ok();
    }

    /**
     * 叉车插入备货区货物接口
     *
     * @param session
     * @param mrfid
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO executeForStorage(HttpSession session, String mrfid) {
        String imei = (String) session.getAttribute(CommonConstant.SESSION_IMEI);
        if (null == imei) {
            //为获取到session中的imei
            return new ResultVO(1001);
        }
        //通过rfid获取入库单和物料信息
        StorageDetailPO storageDetailPO = storageDetailMapper.queryByRfid(mrfid);
        StorageHeadPO storageHeadPO = storageHeadMapper.selectByPrimaryKey(storageDetailPO.getStorageHeadId());
        if (null == storageHeadPO) {
            //没有该入库单
            return new ResultVO(1002);
        }

        //*将当前叉车放入入库单叉车表中，加入执行任务*//*
        //查询叉车表信息
        ForkliftPO forkliftPO = forkliftMapper.queryByImei(imei);
        StorageForkliftPO storageForkliftPO = new StorageForkliftPO();
        storageForkliftPO.setRfid(mrfid);
        storageForkliftPO.setForkliftId(forkliftPO.getForkliftId());
        storageForkliftPO.setStorageHeadId(storageHeadPO.getStorageHeadId());
        storageForkliftMapper.insertSelective(storageForkliftPO);

        // 叉车状态 - 忙碌
        forkliftPO.setStatus(CommonConstant.STATUS_FORKLIFT_BUSY);
        forkliftMapper.updateByPrimaryKey(forkliftPO);

        //发送socket请求
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                //发送socket消息
                WebSocketServer.sendAllMsg(WebSocketVO.createShowVO(storageHeadPO.getStorageHeadId(), CommonConstant.FLAG_STORAGE));
            }
        });

        return ResultVO.ok();
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
    public ResultVO chooseMaterialShow(String mrfid) {
        // 根据栈板rfid查询入库单
        StorageDetailPO storageDetailPO = storageDetailMapper.queryByRfid(mrfid);
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
     * @Description 进入备料区司机选择产品后触发动作
     * @Param
     * @Return
     * @Author AnHongxu.
     * @Date 2020/11/6
     * @Time 15:39
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO chooseMaterialToSpareArea(HttpSession session, StorageDTO dto) {
        // 当前叉车信息
        //String imei = "863958040755311";
        String imei = (String) session.getAttribute(CommonConstant.SESSION_IMEI);
        // 根据imei查询出叉车id
        ForkliftPO forkliftPO = forkliftMapper.queryByImei(imei);
        // 根据栈板rfid查询入库单
        String mrfid = (String) session.getAttribute(CommonConstant.SESSION_MRFID);
        StorageDetailPO storageDetailPO = storageDetailMapper.queryByRfid(mrfid);
        StorageHeadPO storageHeadPO = storageHeadMapper.selectByPrimaryKey(storageDetailPO.getStorageHeadId());
        // 查询当前储位的基本信息
        //String lrfid = (String) session.getAttribute(CommonConstant.SESSION_LRFID);
        LocationPO locationPO = locationMapper.queryByType(CommonConstant.LOCATION_TYPE_PREPARE);
        //# 叉车运送到备货区,rfid 和 入库单解绑,也就是删除其生产来源单号
        //if (locationBO.getLocationTypeId().equals(StorageConstant.TYPE_PREPARATION_AREA) && storageHeadPO.getSourceType().equals(StorageConstant.TYPE_PRODUCT_STORAGE)) {
        // 首先更新入库详情表，添加信息
        //1. 入库详情表更新添加信息
        storageDetailPO.setLocationId(locationPO.getLocationId());
        storageDetailPO.setStorageTime(new Date());
        storageDetailPO.setMaterialId(dto.getMid());
        storageDetailPO.setStorageNum(BigDecimal.ONE);
        storageDetailPO.setStorageStatus(StorageConstant.STATUS_STORED);
        storageDetailPO.setPreparation(StorageConstant.Preparation_YES);
        storageDetailMapper.updateByPrimaryKey(storageDetailPO);
        log.info("更新详情记录表状态变为：已经在备料区" + storageDetailPO.toString());
        //2.入库单表头已入库数量+1,以及判断入库单状态
        if (storageHeadPO.getStorageNum() == null) {
            storageHeadPO.setStorageNum(BigDecimal.ZERO);
        }
        storageHeadPO.setStorageNum(storageHeadPO.getStorageNum().add(BigDecimal.ONE));
        //更新入库单的状态
        if (storageHeadPO.getStorageNum().compareTo(BigDecimal.ONE) == 0) {
            //执行操作记录
            storageRecordMapper.insert(new StorageRecordPO(storageHeadPO.getStorageHeadId(), forkliftPO.getForkliftId(), StorageConstant.OPERATE_NAME_EXECUTE));
        }
        if (storageHeadPO.getStorageNum().compareTo(storageHeadPO.getExpectNum()) == -1) {
            storageHeadPO.setStatus(StorageConstant.STATUS_STOREING);
            //加入入库操作记录
            storageRecordMapper.insert(new StorageRecordPO(storageHeadPO.getStorageHeadId(), forkliftPO.getForkliftId(), StorageConstant.OPERATE_NAME_JOIN));
        } else if (storageHeadPO.getStorageNum().compareTo(storageHeadPO.getExpectNum()) == 0) {
            storageHeadPO.setStatus(StorageConstant.STATUS_STORED);
            storageHeadPO.setStorageTime(new Date());
            //插入入库完成操作记录
            storageRecordMapper.insert(new StorageRecordPO(storageHeadPO.getStorageHeadId(), forkliftPO.getForkliftId(), StorageConstant.OPERATE_NAME_FINISH));
        }
        storageHeadMapper.updateByPrimaryKey(storageHeadPO);
        log.info("更新：更新入库单的状态并入库数量加1" + storageHeadPO.toString());
        //3. 入库单表体已入库数量+1
        // 根据入库单表头id和物料id唯一查找入库单表体，进行数量+1
        StorageBodyPO storageBodyPO = storageBodyMapper.queryByShidAndMid(storageHeadPO.getStorageHeadId(), dto.getMid());
        if (null == storageBodyPO) {
            // 没有该body体
            return new ResultVO(1002, "没有该body体");
        }
        if (storageBodyPO.getStorageNum() == null) {
            storageBodyPO.setStorageNum(BigDecimal.ZERO);
        }
        storageBodyPO.setStorageNum(storageBodyPO.getStorageNum().add(BigDecimal.ONE));
        storageBodyMapper.updateByPrimaryKeySelective(storageBodyPO);
        log.info("更新：更新入库单标体数量入库数量加1" + storageBodyPO.toString());
        //4.释放叉车
        //根据叉车id查询当前执行入库的入库叉车表记录,删除
        StorageForkliftPO storageForkliftPO = storageForkliftMapper.queryByFid(forkliftPO.getForkliftId());
        if (storageForkliftPO != null) {
            storageForkliftMapper.deleteByPrimaryKey(storageForkliftPO.getStorageForkliftId());
            log.info("进行入库备料区后，删除叉车信息" + storageForkliftPO.toString());
        }
        //5. 叉车状态 - 空闲
        forkliftPO.setStatus(CommonConstant.STATUS_FORKLIFT_IDLE);
        forkliftMapper.updateByPrimaryKey(forkliftPO);
        log.info("进行入库备料区后，修改叉车状态为空闲" + forkliftPO.toString());
        //6.库位已经存在的数量+1
        locationPO.setExistNum(locationPO.getExistNum() == null ? BigDecimal.ONE : locationPO.getExistNum().add(BigDecimal.ONE));
        locationMapper.updateByPrimaryKey(locationPO);
        log.info("进行入库备料区后，更新库位信息数量+1" + locationPO.toString());
        //}
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                //发送socket请求
                WebSocketServer.sendAllMsg(WebSocketVO.createShowVO(storageHeadPO.getStorageHeadId(), CommonConstant.FLAG_STORAGE));
                log.info("进行入库备料区后，发送socket请求");
            }
        });
        return ResultVO.ok();
    }


    /**
     * @Description 原产区入库到成品区
     * @Param
     * @Return
     * @Author AnHongxu.
     * @Date 2020/11/7
     * @Time 16:41
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO finishedOriginToStorage(HttpSession session, String mrfid, String lrfid) {

        log.info("取到mrfid为：" + mrfid + "-----------lrfid为：" + lrfid);
        // 当前叉车信息
        //String imei = "863958040755311";
        String imei = (String) session.getAttribute(CommonConstant.SESSION_IMEI);
        // 根据imei查询出叉车id
        ForkliftPO forkliftPO = forkliftMapper.queryByImei(imei);
        // 根据栈板rfid查询入库单
        StorageDetailPO storageDetailPO = storageDetailMapper.queryByRfid(mrfid);
        StorageHeadPO storageHeadPO = storageHeadMapper.selectByPrimaryKey(storageDetailPO.getStorageHeadId());
        // 查询当前储位的基本信息
        LocationBO locationBO = locationMapper.queryByRfid(lrfid);
        log.info("取到的locationBO为：---------------" + locationBO.toString());
        //# 当前储位为成品区并且来源订单类型是生产入库
        //if (locationBO.getLocationTypeId().equals(StorageConstant.TYPE_FINISHED_AREA) && storageHeadPO.getSourceType().equals(StorageConstant.TYPE_PRODUCT_STORAGE)) {
        //1. 入库详情表更新添加信息
        storageDetailPO.setLocationId(locationBO.getLocationId());
        storageDetailPO.setStorageTime(new Date());
        storageDetailPO.setStorageNum(BigDecimal.ONE);
        storageDetailPO.setMaterialId(locationBO.getMaterialId());
        storageDetailPO.setStorageStatus(StorageConstant.STATUS_STORED);
        storageDetailPO.setPreparation(StorageConstant.Preparation_NO);
        storageDetailMapper.updateByPrimaryKey(storageDetailPO);
        log.info("更新详细记录表为：---------------" + storageDetailPO.toString());
        //2.入库单表头已入库数量+1,以及判断入库单状态
        if (storageHeadPO.getStorageNum() == null) {
            storageHeadPO.setStorageNum(BigDecimal.ZERO);
        }
        storageHeadPO.setStorageNum(storageHeadPO.getStorageNum().add(BigDecimal.ONE));
        //存储储位
        storageHeadPO.setWarehouseId(locationBO.getWarehouseId());
        //更新入库单的状态
        if (storageHeadPO.getStorageNum().compareTo(BigDecimal.ONE) == 0) {
            //插入入库执行操作记录
            storageHeadPO.setStatus(StorageConstant.STATUS_STOREING);
            storageRecordMapper.insert(new StorageRecordPO(storageHeadPO.getStorageHeadId(), forkliftPO.getForkliftId(), StorageConstant.OPERATE_NAME_EXECUTE));
        }
        if (storageHeadPO.getStorageNum().compareTo(storageHeadPO.getExpectNum()) == -1) {
            //插入入库参与操作记录
            storageRecordMapper.insert(new StorageRecordPO(storageHeadPO.getStorageHeadId(), forkliftPO.getForkliftId(), StorageConstant.OPERATE_NAME_JOIN));
        } else if (storageHeadPO.getStorageNum().compareTo(storageHeadPO.getExpectNum()) == 0) {
            storageHeadPO.setStatus(StorageConstant.STATUS_STORED);
            storageHeadPO.setStorageTime(new Date());
            //插入入库完成操作记录
            storageRecordMapper.insert(new StorageRecordPO(storageHeadPO.getStorageHeadId(), forkliftPO.getForkliftId(), StorageConstant.OPERATE_NAME_FINISH));
        }
        storageHeadMapper.updateByPrimaryKey(storageHeadPO);
        log.info("入库单表头已入库数量+1,以及判断入库单状态进行更新-------状态变为：" + storageHeadPO.getStatus());
        //3. 入库单表体已入库数量+1
        // 根据入库单表头id和物料id唯一查找入库单表体，进行数量+1
        StorageBodyPO storageBodyPO = storageBodyMapper.queryByShidAndMid(storageHeadPO.getStorageHeadId(), locationBO.getMaterialId());
        if (null == storageBodyPO) {
            // 没有该body体
            return new ResultVO(1002, "没有该body体");
        }
        if (storageBodyPO.getStorageNum() == null) {
            storageBodyPO.setStorageNum(BigDecimal.ZERO);
        }
        storageBodyPO.setStorageNum(storageBodyPO.getStorageNum().add(BigDecimal.ONE));
        storageBodyMapper.updateByPrimaryKeySelective(storageBodyPO);
        log.info("更新入库单body----------：" + storageBodyPO.toString());
        //4.释放叉车
        //根据叉车id查询当前执行入库的入库叉车表记录,删除
        StorageForkliftPO storageForkliftPO = storageForkliftMapper.queryByFid(forkliftPO.getForkliftId());
        if (storageForkliftPO != null) {
            storageForkliftMapper.deleteByPrimaryKey(storageForkliftPO.getStorageForkliftId());
            log.info("删除叉车" + storageForkliftPO.toString());
        }
        //5.叉车状态 - 空闲
        forkliftPO.setStatus(CommonConstant.STATUS_FORKLIFT_IDLE);
        forkliftMapper.updateByPrimaryKey(forkliftPO);
        log.info("更改叉车状态为空闲" + forkliftPO.toString());
        //6.库位已经存在的数量+1
        LocationPO locationPO = locationMapper.selectByPrimaryKey(locationBO.getLocationId());
        locationPO.setExistNum(locationPO.getExistNum() == null ? new BigDecimal(1) : locationPO.getExistNum().add(new BigDecimal(1)));
        locationMapper.updateByPrimaryKey(locationPO);
        log.info("库位已存数量更新" + locationPO.toString());
        //}

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                //发送socket请求
                WebSocketServer.sendAllMsg(WebSocketVO.createShowVO(storageHeadPO.getStorageHeadId(), CommonConstant.FLAG_STORAGE));
                log.info("发送socket请求-------------------------");
            }
        });
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
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO finishedOriginToSpareArea(HttpSession session, String mrfid, String lrfid) {

        // 根据栈板rfid查询入库单
        StorageDetailPO storageDetailPO = storageDetailMapper.queryByRfid(mrfid);
        StorageHeadPO storageHeadPO = storageHeadMapper.selectByPrimaryKey(storageDetailPO.getStorageHeadId());
        List<MaterialPO> pos = storageBodyMapper.queryMaterial(storageHeadPO.getStorageHeadId());
        if (pos.size() == 0) {
            //查不到盖body的物料信息
            return new ResultVO(1001);
        }
        if (pos.size() > 1) {
            sendChooseMaterialShowMsg(pos);
            return ResultVO.ok();
        }
        // 当前叉车信息
        //String imei = "863958040755311";
        String imei = (String) session.getAttribute(CommonConstant.SESSION_IMEI);
        // 根据imei查询出叉车id
        ForkliftPO forkliftPO = forkliftMapper.queryByImei(imei);

        // 查询当前储位的基本信息
        LocationBO locationBO = locationMapper.queryByRfid(lrfid);
        //# 叉车运送到备货区,rfid 和 入库单解绑,也就是删除其生产来源单号
        //if (locationBO.getLocationTypeId().equals(StorageConstant.TYPE_PREPARATION_AREA) && storageHeadPO.getSourceType().equals(StorageConstant.TYPE_PRODUCT_STORAGE)) {
        // 首先更新入库详情表，添加信息
        //1. 入库详情表更新添加信息
        storageDetailPO.setLocationId(locationBO.getLocationId());
        storageDetailPO.setStorageTime(new Date());
        storageDetailPO.setMaterialId(pos.get(0).getMaterialId());
        storageDetailPO.setStorageNum(BigDecimal.ONE);
        storageDetailPO.setStorageStatus(StorageConstant.STATUS_STORED);
        storageDetailPO.setPreparation(StorageConstant.Preparation_YES);
        storageDetailMapper.updateByPrimaryKey(storageDetailPO);
        log.info("更新详情记录表状态变为：已经在备料区" + storageDetailPO.toString());
        //2.入库单表头已入库数量+1,以及判断入库单状态
        if (storageHeadPO.getStorageNum() == null) {
            storageHeadPO.setStorageNum(BigDecimal.ZERO);
        }
        storageHeadPO.setStorageNum(storageHeadPO.getStorageNum().add(BigDecimal.ONE));
        //更新入库单的状态
        if (storageHeadPO.getStorageNum().compareTo(BigDecimal.ONE) == 0) {
            //执行操作记录
            storageRecordMapper.insert(new StorageRecordPO(storageHeadPO.getStorageHeadId(), forkliftPO.getForkliftId(), StorageConstant.OPERATE_NAME_EXECUTE));
        }
        if (storageHeadPO.getStorageNum().compareTo(storageHeadPO.getExpectNum()) == -1) {
            storageHeadPO.setStatus(StorageConstant.STATUS_STOREING);
            //加入入库操作记录
            storageRecordMapper.insert(new StorageRecordPO(storageHeadPO.getStorageHeadId(), forkliftPO.getForkliftId(), StorageConstant.OPERATE_NAME_JOIN));
        } else if (storageHeadPO.getStorageNum().compareTo(storageHeadPO.getExpectNum()) == 0) {
            storageHeadPO.setStatus(StorageConstant.STATUS_STORED);
            storageHeadPO.setStorageTime(new Date());
            //插入入库完成操作记录
            storageRecordMapper.insert(new StorageRecordPO(storageHeadPO.getStorageHeadId(), forkliftPO.getForkliftId(), StorageConstant.OPERATE_NAME_FINISH));
        }
        storageHeadMapper.updateByPrimaryKey(storageHeadPO);
        log.info("更新：更新入库单的状态并入库数量加1" + storageHeadPO.toString());
        //3. 入库单表体已入库数量+1
        // 根据入库单表头id和物料id唯一查找入库单表体，进行数量+1
        StorageBodyPO storageBodyPO = storageBodyMapper.queryByShidAndMid(storageHeadPO.getStorageHeadId(), storageDetailPO.getMaterialId());
        if (null == storageBodyPO) {
            // 没有该body体
            return new ResultVO(1002, "没有该body体");
        }
        if (storageBodyPO.getStorageNum() == null) {
            storageBodyPO.setStorageNum(BigDecimal.ZERO);
        }
        storageBodyPO.setStorageNum(storageBodyPO.getStorageNum().add(BigDecimal.ONE));
        storageBodyMapper.updateByPrimaryKeySelective(storageBodyPO);
        log.info("更新：更新入库单标体数量入库数量加1" + storageBodyPO.toString());
        //4.释放叉车
        //根据叉车id查询当前执行入库的入库叉车表记录,删除
        StorageForkliftPO storageForkliftPO = storageForkliftMapper.queryByFid(forkliftPO.getForkliftId());
        if (storageForkliftPO != null) {
            storageForkliftMapper.deleteByPrimaryKey(storageForkliftPO.getStorageForkliftId());
            log.info("进行入库备料区后，删除叉车信息" + storageForkliftPO.toString());
        }
        //5. 叉车状态 - 空闲
        forkliftPO.setStatus(CommonConstant.STATUS_FORKLIFT_IDLE);
        forkliftMapper.updateByPrimaryKey(forkliftPO);
        log.info("进行入库备料区后，修改叉车状态为空闲" + forkliftPO.toString());
        //6.库位已经存在的数量+1
        LocationPO locationPO = locationMapper.selectByPrimaryKey(locationBO.getLocationId());
        locationPO.setExistNum(locationPO.getExistNum() == null ? BigDecimal.ONE : locationPO.getExistNum().add(BigDecimal.ONE));
        locationMapper.updateByPrimaryKey(locationPO);
        log.info("进行入库备料区后，更新库位信息数量+1" + locationPO.toString());
        //}
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                //发送socket请求
                WebSocketServer.sendAllMsg(WebSocketVO.createShowVO(storageHeadPO.getStorageHeadId(), CommonConstant.FLAG_STORAGE));
                log.info("进行入库备料区后，发送socket请求");
            }
        });
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
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO finishedSpareAreaToStorage(HttpSession session, String mrfid, String lrfid) {
        //String imei = "863958040755311";
        // 当前叉车信息
        String imei = (String) session.getAttribute(CommonConstant.SESSION_IMEI);
        // 根据imei查询出叉车id
        ForkliftPO forkliftPO = forkliftMapper.queryByImei(imei);
        // 根据栈板rfid查询之前的入库单
        StorageDetailPO storageDetailPO = storageDetailMapper.queryByRfid(mrfid);
        // 查询当前储位的基本信息
        LocationBO locationBO = locationMapper.queryByRfid(lrfid);
        //入库单id全局变量
        Long storageHeadId = null;
        //# 阅读器扫描成品区库位，并且来源是备货入库单，入库第一条成功后，此时生成备货入库单
        //if (locationBO.getLocationTypeId().equals(StorageConstant.TYPE_FINISHED_AREA) && storageDetailPO.getPreparation().equals(StorageConstant.Preparation_YES)) {
        // 看是不是当前时间段备料区入库的第一单，如果是第一单，生成备料区入库单
        //根据来源类型查询是否当前有备料区入库单
        List<StorageHeadPO> storageHeadPOS = storageHeadMapper.queryByStatus(StorageConstant.STATUS_STOREING, StorageConstant.TYPE_PRE_STORAGE);
        if (storageHeadPOS.size() <= 0) {
            // 如果当前时间段没有备料入库单，那么就新增一条备料入库单
            // 生成备料区入库单表头
            StorageHeadPO storageHeadPO = new StorageHeadPO();
            storageHeadPO.setStorageNo(StorageNoUtil.genStorageHeadNo(storageHeadMapper, StorageNoUtil.RECEIPT_HEAD_YP, new Date()));
            storageHeadPO.setSourceType(StorageConstant.TYPE_PRE_STORAGE);
            storageHeadPO.setStorageNum(BigDecimal.ONE);
            storageHeadPO.setStatus(StorageConstant.STATUS_STOREING);
            //存储储位
            storageHeadPO.setWarehouseId(locationBO.getWarehouseId());
            storageHeadPO.setCreateTime(new Date());
            storageHeadPO.setDr((byte) 1);
            storageHeadMapper.insert(storageHeadPO);
            log.info("进行备料区入成品区，是不是当前时间段备料区入库的第一单，如果是第一单，生成备料区入库单-----" + storageHeadPO.toString());
            //生成一条对应的入库单表体
            // 只生成一个入库单表体
            StorageBodyPO storageBodyPO = new StorageBodyPO();
            storageBodyPO.setStorageHeadId(storageHeadPO.getStorageHeadId());
            storageBodyPO.setMaterialId(storageDetailPO.getMaterialId());
            storageBodyPO.setStorageNum(BigDecimal.ONE);
            storageBodyPO.setCreateTime(new Date());
            storageBodyPO.setDr((byte) 1);
            storageBodyMapper.insert(storageBodyPO);
            log.info("进行备料区入成品区，是不是当前时间段备料区入库的第一单，如果是第一单，生成备料区入库单标体-----" + storageBodyPO.toString());
            //更新详情记录表变为已经不在备料区了，并添加一条新的记录
            storageDetailPO.setPreparation((byte) 1);
            storageDetailMapper.updateByPrimaryKey(storageDetailPO);
            log.info("进行备料区入成品区，更新详情记录表变为已经不在备料区了-----" + storageDetailPO.toString());
            //添加入库记录
            /*StorageDetailPO poForStorage = new StorageDetailPO();
            poForStorage.setStorageHeadId(storageBodyPO.getStorageHeadId());
            poForStorage.setLocationId(locationBO.getLocationId());
            poForStorage.setMaterialId(storageDetailPO.getMaterialId());
            poForStorage.setStorageNum(BigDecimal.ONE);
            poForStorage.setStorageTime(new Date());
            poForStorage.setRfid(mrfid);
            poForStorage.setStorageStatus(StorageConstant.STATUS_STORED);
            poForStorage.setPreparation(StorageConstant.Preparation_NO);
            storageDetailMapper.insertSelective(poForStorage);
            log.info("进行备料区入成品区，添加一条备料区入成品区的详细记录-----" + poForStorage.toString());*/
            //更新入库单的状态
            if (storageHeadPO.getStorageNum().compareTo(BigDecimal.ONE) == 0) {
                //插入入单执行操作
                storageRecordMapper.insert(new StorageRecordPO(storageHeadPO.getStorageHeadId(), forkliftPO.getForkliftId(), StorageConstant.OPERATE_NAME_EXECUTE));
                storageHeadPO.setStatus(StorageConstant.STATUS_STOREING);
                storageHeadMapper.updateByPrimaryKey(storageHeadPO);
                log.info("进行备料区入成品区，更新入库状态为进行中-----" + storageHeadPO.toString());
            } else {
                //插入入库参与操作记录
                storageRecordMapper.insert(new StorageRecordPO(storageHeadPO.getStorageHeadId(), forkliftPO.getForkliftId(), StorageConstant.OPERATE_NAME_JOIN));
            }
            storageHeadId = storageHeadPO.getStorageHeadId();
        } else {
            //已经有入库单了，加入到有的入库单中，更新入库单入库数量，以及更新或添加表体信息
            StorageHeadPO storageHeadPO = storageHeadPOS.get(0);
            if (storageHeadPO.getStorageNum() == null) {
                storageHeadPO.setStorageNum(BigDecimal.ZERO);
            }
            storageHeadPO.setStorageNum(storageHeadPO.getStorageNum().add(BigDecimal.ONE));
            //存储储位
            storageHeadPO.setWarehouseId(locationBO.getWarehouseId());
            //根据表头和物料id查看是否之前有该备料入库单的表体，如果有更新，没有进行添加
            StorageBodyPO storageBodyPO = storageBodyMapper.queryByShidAndMid(storageHeadPO.getStorageHeadId(), locationBO.getMaterialId());
            if (storageBodyPO == null) {
                //创建新的表体
                StorageBodyPO storageBodyPONew = new StorageBodyPO();
                storageBodyPONew.setStorageHeadId(storageHeadPO.getStorageHeadId());
                storageBodyPONew.setMaterialId(storageDetailPO.getMaterialId());
                storageBodyPONew.setStorageNum(BigDecimal.ONE);
                storageBodyPONew.setCreateTime(new Date());
                storageBodyPONew.setDr((byte) 1);
                storageBodyMapper.insert(storageBodyPONew);
                log.info("进行备料区入成品区，如果之前没有标体，创建新的表体-----" + storageBodyPONew.toString());
            } else {
                //更新原来的表体
                if (storageBodyPO.getStorageNum() == null) {
                    storageBodyPO.setStorageNum(BigDecimal.ZERO);
                }
                storageBodyPO.setStorageNum(storageBodyPO.getStorageNum().add(BigDecimal.ONE));
                storageBodyMapper.updateByPrimaryKey(storageBodyPO);
                log.info("进行备料区入成品区，如果之前有这个标体，更新这个标体-----" + storageBodyPO.toString());
            }
            //更新详情记录表变为已经不在备料区了，并添加一条新的记录
            storageDetailPO.setPreparation((byte) 1);
            storageDetailMapper.updateByPrimaryKey(storageDetailPO);
            log.info("进行备料区入成品区，更新详情记录表变为已经不在备料区了-----" + storageDetailPO.toString());
            //添加入库记录
           /* StorageDetailPO poForStorage = new StorageDetailPO();
            poForStorage.setStorageHeadId(storageHeadPO.getStorageHeadId());
            poForStorage.setLocationId(locationBO.getLocationId());
            poForStorage.setMaterialId(storageDetailPO.getMaterialId());
            poForStorage.setStorageNum(BigDecimal.ONE);
            poForStorage.setStorageTime(new Date());
            poForStorage.setRfid(mrfid);
            poForStorage.setStorageStatus(StorageConstant.STATUS_STORED);
            poForStorage.setPreparation(StorageConstant.Preparation_NO);
            storageDetailMapper.insertSelective(poForStorage);
            log.info("进行备料区入成品区，添加一条新的记录-----" + poForStorage.toString());*/
            //更新入库单的状态
            if (storageHeadPO.getStorageNum().compareTo(BigDecimal.ONE) == 0) {
                //插入入单执行操作
                storageRecordMapper.insert(new StorageRecordPO(storageHeadPO.getStorageHeadId(), forkliftPO.getForkliftId(), StorageConstant.OPERATE_NAME_EXECUTE));
            } else {
                //插入入库参与操作记录
                storageRecordMapper.insert(new StorageRecordPO(storageHeadPO.getStorageHeadId(), forkliftPO.getForkliftId(), StorageConstant.OPERATE_NAME_JOIN));
            }
            storageHeadId = storageHeadPO.getStorageHeadId();
        }
        //将当前运作的叉车删掉
        //根据叉车id查询当前执行入库的入库叉车表记录,删除
        StorageForkliftPO storageForkliftPO = storageForkliftMapper.queryByFid(forkliftPO.getForkliftId());
        if (storageForkliftPO != null) {
            storageForkliftMapper.deleteByPrimaryKey(storageForkliftPO.getStorageForkliftId());
            log.info("进行备料区入成品区，删掉该叉车-----" + storageForkliftPO.toString());
        }
        // 叉车状态 - 空闲
        forkliftPO.setStatus(CommonConstant.STATUS_FORKLIFT_IDLE);
        forkliftMapper.updateByPrimaryKey(forkliftPO);
        log.info("进行备料区入成品区，将该叉车变为空闲-----" + forkliftPO.toString());
        //成品库位已经存在的数量+1
        LocationPO locationPO = locationMapper.selectByPrimaryKey(locationBO.getLocationId());
        locationPO.setExistNum(locationPO.getExistNum() == null ? BigDecimal.ONE : locationPO.getExistNum().add(BigDecimal.ONE));
        locationMapper.updateByPrimaryKey(locationPO);
        log.info("进行备料区入成品区，成品库位已经存在的数量+1-----" + locationPO.toString());
        //备料区已存库位-1
        //通过rfid查找备料区的库位id
        LocationPO locationPOForPre = locationMapper.selectByPrimaryKey(storageDetailPO.getLocationId());
        locationPOForPre.setExistNum(locationPO.getExistNum() == null ? BigDecimal.ONE : locationPO.getExistNum().subtract(BigDecimal.ONE));
        locationMapper.updateByPrimaryKey(locationPOForPre);
        log.info("进行备料区入成品区，备料区的库位id存在的数量-1-----" + locationPOForPre.toString());
        //}

        Long finalStorageHeadId = storageHeadId;
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                //发送socket请求
                WebSocketServer.sendAllMsg(WebSocketVO.createShowVO(finalStorageHeadId, CommonConstant.FLAG_STORAGE));
                log.info("进行备料区入成品区，发送socket请求-----");
            }
        });
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
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO finishedMove(HttpSession session, String mrfid, String lrfid, Byte ltype) {
        //String imei = "863958040755311";
        // 当前叉车信息
        String imei = (String) session.getAttribute(CommonConstant.SESSION_IMEI);
        // 根据imei查询出叉车id
        ForkliftPO forkliftPO = forkliftMapper.queryByImei(imei);
        // 根据栈板rfid查询之前的入库单详情记录
        StorageDetailPO storageDetailPO = storageDetailMapper.queryByRfid(mrfid);
        // 查询当前储位的基本信息
        LocationBO locationBO = locationMapper.queryByRfid(lrfid);
        //更新库位
        storageDetailPO.setLocationId(locationBO.getLocationId());
        if (ltype.equals(1)) {
            storageDetailPO.setPreparation(StorageConstant.Preparation_NO);
        } else if (ltype.equals(2)) {
            storageDetailPO.setPreparation(StorageConstant.Preparation_YES);
        } else {
            //既不是备料区也不是成品区，啥区也不是
            return new ResultVO(1001);
        }
        //更新库位和是在哪个区标识
        storageDetailMapper.updateByPrimaryKey(storageDetailPO);
        //将当前运作的叉车删掉
        //根据叉车id查询当前执行入库的入库叉车表记录,删除
        StorageForkliftPO storageForkliftPO = storageForkliftMapper.queryByFid(forkliftPO.getForkliftId());
        if (storageForkliftPO != null) {
            storageForkliftMapper.deleteByPrimaryKey(storageForkliftPO.getStorageForkliftId());
            log.info("进行库内平移，删掉该叉车-----" + storageForkliftPO.toString());
        }
        // 叉车状态 - 空闲
        forkliftPO.setStatus(CommonConstant.STATUS_FORKLIFT_IDLE);
        forkliftMapper.updateByPrimaryKey(forkliftPO);
        log.info("进行库内平移，将该叉车变为空闲-----" + forkliftPO.toString());
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                //发送socket请求
                WebSocketServer.sendAllMsg(WebSocketVO.createShowVO(storageDetailPO.getStorageHeadId(), CommonConstant.FLAG_STORAGE));
                log.info("进行备料区入成品区，发送socket请求-----");
            }
        });
        return ResultVO.ok();
    }

    /**
     * @Description 原产区到原产品放下
     * @Param
     * @Return
     * @Author AnHongxu.
     * @Date 2020/11/14
     * @Time 16:40
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO finishedOriginToOrigin(HttpSession session, String mrfid, String lrfid) {
        //String imei = "863958040755311";
        // 当前叉车信息
        String imei = (String) session.getAttribute(CommonConstant.SESSION_IMEI);
        // 根据imei查询出叉车id
        ForkliftPO forkliftPO = forkliftMapper.queryByImei(imei);
        //根据叉车id查询当前执行入库的入库叉车表记录,删除
        StorageForkliftPO storageForkliftPO = storageForkliftMapper.queryByFid(forkliftPO.getForkliftId());
        // 根据栈板rfid查询之前的入库单详情记录
        StorageDetailPO storageDetailPO = storageDetailMapper.queryByRfid(mrfid);
        if (storageForkliftPO != null) {
            storageForkliftMapper.deleteByPrimaryKey(storageForkliftPO.getStorageForkliftId());
            log.info("原产区到原产区，删掉该叉车-----" + storageForkliftPO.toString());
        }
        // 叉车状态 - 空闲
        forkliftPO.setStatus(CommonConstant.STATUS_FORKLIFT_IDLE);
        forkliftMapper.updateByPrimaryKey(forkliftPO);
        log.info("原产区到原产区，将该叉车变为空闲-----" + forkliftPO.toString());
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                //发送socket请求
                WebSocketServer.sendAllMsg(WebSocketVO.createShowVO(storageDetailPO.getStorageHeadId(), CommonConstant.FLAG_STORAGE));
                log.info("原产区到原产区，发送socket请求-----");
            }
        });
        return ResultVO.ok();
    }
}

