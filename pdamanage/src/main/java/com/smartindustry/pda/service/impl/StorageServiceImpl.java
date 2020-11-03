package com.smartindustry.pda.service.impl;

import com.smartindustry.common.bo.sm.StorageBodyBO;
import com.smartindustry.common.bo.sm.StorageHeadBO;
import com.smartindustry.common.mapper.si.ForkliftMapper;
import com.smartindustry.common.mapper.si.LocationMapper;
import com.smartindustry.common.mapper.sm.StorageBodyMapper;
import com.smartindustry.common.mapper.sm.StorageHeadMapper;
import com.smartindustry.common.mapper.sm.StorageRecordMapper;
import com.smartindustry.common.mapper.wo.ProduceOrderMapper;
import com.smartindustry.common.pojo.si.ForkliftPO;
import com.smartindustry.common.pojo.si.LocationPO;
import com.smartindustry.common.pojo.sm.StorageBodyPO;
import com.smartindustry.common.pojo.sm.StorageHeadPO;
import com.smartindustry.common.pojo.sm.StorageRecordPO;
import com.smartindustry.common.pojo.wo.ProduceOrderPO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.pda.constant.StorageConstant;
import com.smartindustry.pda.dto.OperateDTO;
import com.smartindustry.pda.dto.StorageDTO;
import com.smartindustry.pda.service.IStorageService;
import com.smartindustry.pda.util.StorageNoUtil;
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
    private LocationMapper locationMapper;

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
        int colorIndex = 0;
        for (StorageBodyBO bo : storageHeadBO.getBos()) {
            StorageDetailVO.LocationVO lvo = new StorageDetailVO.LocationVO();
            lvo.setColor(StorageDetailVO.COLORS[colorIndex]);
            lvo.setMinfo(bo.getMaterialName() + " " + bo.getMaterialModel());
            lvos.put(bo.getMaterialId(), lvo);
            colorIndex += colorIndex;
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
            String imei = (String) session.getAttribute(StorageConstant.SESSION_IMEI);
            List<String> fnames = new ArrayList<>(pos.size());
            for (ForkliftPO po : pos) {
                fnames.add(po.getForkliftNo());
            }
            vo.setFnames(fnames);
        }

        session.setAttribute(StorageConstant.SESSION_SHID, dto.getShid());

        return ResultVO.ok().setData(vo);
    }


    @Override
    public ResultVO forkliftLift(Map<String, Object> reqData) {
        return null;
    }
}
