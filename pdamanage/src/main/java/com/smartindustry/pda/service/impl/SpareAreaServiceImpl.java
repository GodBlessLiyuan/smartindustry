package com.smartindustry.pda.service.impl;

import com.smartindustry.common.bo.si.LocationBO;
import com.smartindustry.common.bo.sm.StorageHeadBO;
import com.smartindustry.common.mapper.si.ForkliftMapper;
import com.smartindustry.common.mapper.si.LocationMapper;
import com.smartindustry.common.mapper.si.MaterialMapper;
import com.smartindustry.common.mapper.sm.*;
import com.smartindustry.common.pojo.om.OutboundHeadPO;
import com.smartindustry.common.pojo.si.ForkliftPO;
import com.smartindustry.common.pojo.si.MaterialPO;
import com.smartindustry.common.pojo.sm.*;
import com.smartindustry.common.util.DateUtil;
import com.smartindustry.common.vo.ResultVO;

import com.smartindustry.pda.constant.CommonConstant;
import com.smartindustry.pda.constant.StorageConstant;
import com.smartindustry.pda.dto.StoragePreDTO;
import com.smartindustry.pda.service.ISpareAreaService;
import com.smartindustry.pda.socket.WebSocketServer;
import com.smartindustry.pda.socket.WebSocketVO;
import com.smartindustry.pda.util.StorageNoUtil;
import com.smartindustry.pda.vo.MaterialVO;
import com.smartindustry.pda.vo.SpareMaterialVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 11:27 2020/11/2
 * @version: 1.0.0
 * @description:
 */
@Service
public class SpareAreaServiceImpl implements ISpareAreaService {
    @Autowired
    private LocationMapper locationMapper;
    @Autowired
    private StorageHeadMapper storageHeadMapper;
    @Autowired
    private StorageBodyMapper storageBodyMapper;
    @Autowired
    private ForkliftMapper forkliftMapper;
    @Autowired
    private StorageDetailMapper storageDetailMapper;
    @Autowired
    private MaterialMapper materialMapper;
    @Autowired
    private StorageForkliftMapper storageForkliftMapper;
    @Autowired
    StorageRecordMapper storageRecordMapper;

    @Override
    public ResultVO enterSpare(StoragePreDTO dto, HttpSession session) {
        // 当前叉车信息
        String imei = (String) session.getAttribute(CommonConstant.SESSION_IMEI);
        // 根据imei查询出叉车id
        ForkliftPO forkliftPO = forkliftMapper.queryByImei(imei);
        // 根据栈板rfid查询入库单
        StorageDetailPO storageDetailPO = storageDetailMapper.queryByRfid(dto.getMrfid());
        StorageHeadPO storageHeadPO = storageHeadMapper.selectByPrimaryKey(storageDetailPO.getStorageHeadId());
        // 查询当前储位的基本信息
        Date date = new Date();
        LocationBO locationBO = locationMapper.queryByRfid(dto.getLrfid());

        // 当前入库表体
        StorageBodyPO bodyPO = storageBodyMapper.queryByShidAndMid(storageHeadPO.getStorageHeadId(), locationBO.getMaterialId());

        //# 当前储位为成品区并且来源订单类型是生产入库
        if (locationBO.getLocationTypeId().equals(StorageConstant.TYPE_FINISHED_AREA) && storageHeadPO.getSourceType().equals(StorageConstant.TYPE_PRODUCT_STORAGE)) {
            //1. 新增入库详情表
            StorageDetailPO detailPO = new StorageDetailPO();
            detailPO.setLocationId(locationBO.getLocationId());
            detailPO.setStorageTime(date);
            detailPO.setRfid(dto.getMrfid());
            storageDetailMapper.insert(detailPO);
            //2. 入库单表体已入库数量+1
            if (null != bodyPO) {
                bodyPO.setStorageNum(bodyPO.getStorageNum().add(new BigDecimal(1)));
                storageBodyMapper.updateByPrimaryKeySelective(bodyPO);
            } else {
                bodyPO.setStorageNum(new BigDecimal(1));
                bodyPO.setStorageHeadId(storageHeadPO.getStorageHeadId());
                bodyPO.setLocationId(locationBO.getLocationId());
                bodyPO.setCreateTime(date);
                bodyPO.setDr((byte) 1);
                storageBodyMapper.insert(bodyPO);
            }
            storageHeadPO.setStorageNum(bodyPO.getStorageNum().add(new BigDecimal(1)));
            //判断入库单的状态
            if (storageHeadPO.getStorageNum().add(new BigDecimal(1)).compareTo(storageHeadPO.getExpectNum()) == -1) {
                storageHeadPO.setStatus(StorageConstant.STATUS_STOREING);
            } else {
                storageHeadPO.setStatus(StorageConstant.STATUS_STORED);
                storageHeadPO.setStorageTime(date);
            }
            // 判断储位是否已满
            if (!judgeFull(locationBO.getLocationId())) {
                // 储位已满
                return new ResultVO(1007);
            }
            storageHeadMapper.updateByPrimaryKeySelective(storageHeadPO);
        }

        //# 叉车运送到备货区,rfid 和 入库单解绑,也就是删除其生产来源单号
        if (locationBO.getLocationTypeId().equals(StorageConstant.TYPE_PREPARATION_AREA) && storageHeadPO.getSourceType().equals(StorageConstant.TYPE_PRODUCT_STORAGE)) {
            // 首先新增入库详情表
            StorageDetailPO detailPO = new StorageDetailPO();
            detailPO.setLocationId(locationBO.getLocationId());
            detailPO.setStorageTime(date);
            detailPO.setRfid(dto.getMrfid());
            detailPO.setMaterialId(dto.getMid());
            storageDetailMapper.insert(detailPO);
            // 解绑,就是将入库单的来源单号给置空,根据入库单表头id和rfid删除
            storageDetailMapper.deleteByShidAndRfid(storageHeadPO.getStorageHeadId(), dto.getMrfid());
            // 判断储位是否已满
            if (!judgeFull(locationBO.getLocationId())) {
                // 储位已满
                return new ResultVO(1007);
            }
        }

        //# 阅读器扫描备货区成品，准备运往成品区
        if (locationBO.getLocationTypeId().equals(StorageConstant.TYPE_PREPARATION_AREA) && storageHeadPO == null) {
            // 那么入库详情表需要删除
            storageDetailMapper.deleteDetail(locationBO.getLocationId(), dto.getMrfid());
        }

        //# 阅读器扫描成品区库位，并且来源是备货入库单，入库一条成功后，此时生成备货入库单
        if (locationBO.getLocationTypeId().equals(StorageConstant.TYPE_FINISHED_AREA) && storageHeadPO == null) {
            // 如果当前时间段没有备料入库单，那么就新增一条备料入库单
            Map<String, Date> map = DateUtil.belongCalendar(date);
            StorageHeadPO po = storageHeadMapper.queryPrepareNo(new Date(), map.get("start"), map.get("end"));
            if (null == po) {
                // 生成备料区入库单
                StorageHeadPO headPO1 = new StorageHeadPO();
                headPO1.setStorageNo(StorageNoUtil.genStorageHeadNo(storageHeadMapper, StorageNoUtil.RECEIPT_HEAD_YP, new Date()));
                headPO1.setSourceType(StorageConstant.TYPE_PRE_STORAGE);
                headPO1.setStorageNum(new BigDecimal(1));
                headPO1.setStatus(StorageConstant.STATUS_STOREING);
                headPO1.setCreateTime(date);
                headPO1.setDr((byte) 1);
                storageHeadMapper.insert(headPO1);
            } else {
                po.setStorageNum(po.getStorageNum().add(new BigDecimal(1)));
                storageHeadMapper.updateByPrimaryKey(po);
            }
            StorageDetailPO detailPO = new StorageDetailPO();
            detailPO.setLocationId(locationBO.getLocationId());
            detailPO.setStorageTime(date);
            detailPO.setRfid(dto.getMrfid());
            detailPO.setStorageStatus(StorageConstant.STATUS_STORED);
            // 判断储位是否已满
            if (!judgeFull(locationBO.getLocationId())) {
                // 储位已满
                return new ResultVO(1007);
            }
        }
        return ResultVO.ok();
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
            List<StorageHeadPO> storageHeadPOS = storageHeadMapper.queryByStatus(StorageConstant.STATUS_STOREING,StorageConstant.TYPE_PRE_STORAGE);
            if(storageHeadPOS.size()<=0){
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
            } else {
                //已经有入库单了，加入到有的入库单中，更新入库单入库数量，以及更新或添加表体信息
                StorageHeadPO storageHeadPO = storageHeadPOS.get(0);
                storageHeadPO.setStorageNum(storageHeadPO.getStorageNum().add(new BigDecimal(1)));
                //根据表头和物料id查看是否之前有该备料入库单的表体，如果有更新，没有进行添加
                StorageBodyPO storageBodyPO = storageBodyMapper.queryByShidAndMid(storageHeadPO.getStorageHeadId(), locationBO.getMaterialId());


            }
            StorageDetailPO detailPO = new StorageDetailPO();
            detailPO.setLocationId(locationBO.getLocationId());
            detailPO.setStorageTime(new Date());
            detailPO.setRfid(dto.getMrfid());
            detailPO.setStorageStatus(StorageConstant.STATUS_STORED);
            // 判断储位是否已满
            if (!judgeFull(locationBO.getLocationId())) {
                // 储位已满
                return new ResultVO(1007);
            }
        }
        //返回Socket视图


        return ResultVO.ok();
    }


}
