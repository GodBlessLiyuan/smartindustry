package com.smartindustry.storage.service.impl;

import com.smartindustry.common.bo.si.LocationBO;
import com.smartindustry.common.mapper.si.LocationMapper;
import com.smartindustry.common.mapper.sm.StorageBodyMapper;
import com.smartindustry.common.mapper.sm.StorageHeadMapper;
import com.smartindustry.common.pojo.sm.StorageBodyPO;
import com.smartindustry.common.pojo.sm.StorageDetailPO;
import com.smartindustry.common.pojo.sm.StorageHeadPO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.constant.StorageConstant;
import com.smartindustry.storage.dto.StoragePreDTO;
import com.smartindustry.storage.service.ISpareAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

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

    @Override
    public ResultVO enterSpare(StoragePreDTO dto){
        // 查询当前储位的基本信息
        LocationBO bo = locationMapper.queryByRfid(dto.getLrfid());
        StorageHeadPO headPO = storageHeadMapper.selectByPrimaryKey(dto.getSid());
        StorageBodyPO bodyPO = storageBodyMapper.queryByShidAndMid(dto.getSid(),dto.getMid());
        // 当前储位为成品区并且订单类型是成品入库
        if(bo.getLocationTypeId().equals(StorageConstant.TYPE_FINISHED_AREA) && headPO.getSourceType().equals(StorageConstant.TYPE_PRODUCT_STORAGE)){
            //1. 新增入库详情表
            StorageDetailPO detailPO = new StorageDetailPO();
            detailPO.setStorageBodyId(bodyPO.getStorageBodyId());
            detailPO.setLocationId(bo.getLocationId());
            detailPO.setStorageTime(new Date());
            detailPO.setRfid(dto.getPrfid());
            //2. 入库单表体已入库数量+1
            if(null != bodyPO){
                bodyPO.setStorageNum(bodyPO.getStorageNum().add(new BigDecimal(1)));
                storageBodyMapper.updateByPrimaryKeySelective(bodyPO);
            }else {
                bodyPO.setStorageNum(new BigDecimal(1));
                bodyPO.setStorageHeadId(dto.getSid());
                bodyPO.setMaterialId(dto.getMid());
                bodyPO.setLocationId(bo.getLocationId());
                Date date = new Date();
                bodyPO.setAcceptTime(date);
                bodyPO.setCreateTime(date);
                bodyPO.setDr((byte) 1);
                storageBodyMapper.insert(bodyPO);
            }
            headPO.setStorageNum(bodyPO.getStorageNum().add(new BigDecimal(1)));
            //判断入库单的状态
            if(headPO.getStorageNum().add(new BigDecimal(1)).compareTo(headPO.getExpectNum()) == -1){
                headPO.setStatus(StorageConstant.STATUS_INSTORAGE);
            }else {
                headPO.setStatus(StorageConstant.STATUS_STORED);
                headPO.setStorageTime(new Date());
            }
            storageHeadMapper.updateByPrimaryKeySelective(headPO);
        }
        //进入备货区
        if(bo.getLocationTypeId().equals(StorageConstant.TYPE_PREPARATION_AREA)){
            //当前栈板和入库单解绑

        }
        return ResultVO.ok();
    }
}
