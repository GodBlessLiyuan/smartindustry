package com.smartindustry.pda.service.impl;

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
import com.smartindustry.pda.service.IStorageService;
import com.smartindustry.pda.util.StorageNoUtil;
import com.smartindustry.pda.vo.StorageDetailVO;
import org.apache.poi.hpsf.Decimal;
import org.springframework.beans.factory.annotation.Autowired;
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
            storageBodyMapper.insert(storageBodyPO);
        } else {
            List<StorageBodyPO> storageBodyPOS = new ArrayList<>(2);
            StorageBodyPO storageBodyPO1 = new StorageBodyPO();
            storageBodyPO1.setStorageHeadId(storageHeadPO.getStorageHeadId());
            storageBodyPO1.setMaterialId(produceOrderPO.getMaterialId1());
            storageBodyPO1.setCreateTime(new Date());
            storageBodyPOS.add(storageBodyPO1);

            StorageBodyPO storageBodyPO2 = new StorageBodyPO();
            storageBodyPO2.setStorageHeadId(storageHeadPO.getStorageHeadId());
            storageBodyPO2.setMaterialId(produceOrderPO.getMaterialId2());
            storageBodyPO2.setCreateTime(new Date());
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
        session.setAttribute(CommonConstant.SESSION_SHID, dto.getShid());
        return ResultVO.ok().setData(vo);
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

        List<StorageBodyBO> bodyBOList= new ArrayList<>(1);
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
}
