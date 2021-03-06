package com.smartindustry.storage.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.common.bo.si.MaterialBO;
import com.smartindustry.common.bo.si.WarehouseBO;
import com.smartindustry.common.bo.sm.StorageHeadBO;
import com.smartindustry.common.bo.sm.StorageRecordBO;
import com.smartindustry.common.constant.ExceptionEnums;
import com.smartindustry.common.mapper.si.MaterialMapper;
import com.smartindustry.common.mapper.si.SupplierMapper;
import com.smartindustry.common.mapper.si.WarehouseMapper;
import com.smartindustry.common.mapper.sm.StorageBodyMapper;
import com.smartindustry.common.mapper.sm.StorageHeadMapper;
import com.smartindustry.common.mapper.sm.StorageRecordMapper;
import com.smartindustry.common.pojo.si.SupplierPO;
import com.smartindustry.common.pojo.sm.StorageBodyPO;
import com.smartindustry.common.pojo.sm.StorageHeadPO;
import com.smartindustry.common.pojo.sm.StorageRecordPO;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.constant.StorageConstant;
import com.smartindustry.storage.dto.OperateDTO;
import com.smartindustry.storage.dto.StorageHeadDTO;
import com.smartindustry.storage.service.IPurchaseStorageService;
import com.smartindustry.storage.util.StorageNoUtil;
import com.smartindustry.storage.vo.MaterialVO;
import com.smartindustry.storage.vo.StorageHeadVO;
import com.smartindustry.storage.vo.StorageRecordVO;
import com.smartindustry.storage.vo.WarehouseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 19:52 2020/10/26
 * @version: 1.0.0
 * @description:
 */
@EnableTransactionManagement
@Service
public class PurchaseStorageServiceImpl implements IPurchaseStorageService {
    @Autowired
    private StorageHeadMapper storageHeadMapper;
    @Autowired
    private StorageBodyMapper storageBodyMapper;
    @Autowired
    private WarehouseMapper warehouseMapper;
    @Autowired
    private MaterialMapper materialMapper;
    @Autowired
    private StorageRecordMapper storageRecordMapper;
    @Autowired
    private SupplierMapper supplierMapper;

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData){
        Page<StorageHeadBO> page = PageQueryUtil.startPage(reqData);
        List<StorageHeadBO> bos = storageHeadMapper.pageQuery(reqData);

        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), StorageHeadVO.convert(bos)));
    }

    @Override
    public ResultVO detail(OperateDTO dto){
        StorageHeadBO bo = storageHeadMapper.queryByShid(dto.getShid());
        if(null == bo){
            // 采购单表体不存在
            return new ResultVO(ExceptionEnums.NO_EXIST.getCode());
        }
        return ResultVO.ok().setData(StorageHeadVO.convertVO(bo));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO edit(StorageHeadDTO dto){
        if (null == dto.getShid()) {
            // 采购入库单表头
            StorageHeadPO po = StorageHeadDTO.createPO(dto);
            po.setStorageNo(StorageNoUtil.genStorageHeadNo(storageHeadMapper,StorageNoUtil.RECEIPT_HEAD_YP,new Date()));
            if(dto.getFlag()){
                po.setStorageNum(dto.getBody().stream().map(x -> x.getAnum()).reduce(BigDecimal.ZERO,BigDecimal::add));
                po.setStatus(StorageConstant.STATUS_STORED);
                po.setStorageTime(new Date());
            }else {
                po.setStatus(StorageConstant.STATUS_STOREING);
            }
            storageHeadMapper.insert(po);
            if(!dto.getBody().isEmpty()){
                // 采购入库单表体
                List<StorageBodyPO> pos = StorageHeadDTO.convert(po,dto.getBody());
                storageBodyMapper.batchInsert(pos);
            }
            if(dto.getFlag()){
                storageRecordMapper.insert(new StorageRecordPO(po.getStorageHeadId(),1L,StorageConstant.OPERATE_NAME_AGREE));
            }else {
                storageRecordMapper.insert(new StorageRecordPO(po.getStorageHeadId(),1L,StorageConstant.OPERATE_NAME_INSERT));
            }
            return ResultVO.ok();
        }
        StorageHeadPO po = storageHeadMapper.selectByPrimaryKey(dto.getShid());
        if (null == po) {
            // 采购单表头不存在
            return new ResultVO(ExceptionEnums.NO_EXIST.getCode());
        }
        if(dto.getFlag()){
            po.setStorageNum(dto.getBody().stream().map(x -> x.getAnum()).reduce(BigDecimal.ZERO,BigDecimal::add));
            po.setStatus(StorageConstant.STATUS_STORED);
            po.setStorageTime(new Date());
        }else {
            po.setStatus(StorageConstant.STATUS_STOREING);
        }
        StorageHeadPO headPO = StorageHeadDTO.buildPO(po,dto);
        storageHeadMapper.updateByPrimaryKeySelective(headPO);
        // 首先先删除采购入库单所有的表体
        List<Long> sbids = storageBodyMapper.querySbids(dto.getShid());
        storageBodyMapper.deleteBatch(sbids);
        if(!dto.getBody().isEmpty()){
            // 采购入库单表体
            List<StorageBodyPO> pos = StorageHeadDTO.convert(po,dto.getBody());
            storageBodyMapper.batchInsert(pos);
        }
        return ResultVO.ok();
    }

    @Override
    public ResultVO queryLocation(){
        List<WarehouseBO> bos = warehouseMapper.queryAll();
        return ResultVO.ok().setData(WarehouseVO.convert(bos));
    }

    @Override
    public ResultVO queryMaterial(Map<String, Object> reqData){
        Page<MaterialBO> page = PageQueryUtil.startPage(reqData);
        List<MaterialBO> bos = materialMapper.pageQueryStorage(reqData);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), MaterialVO.convert(bos)));
    }


    @Override
    public ResultVO deleteBody(List<Long> sbids){
        storageBodyMapper.deleteBatch(sbids);
        return ResultVO.ok();
    }

    @Override
    public ResultVO queryStorageRecord(OperateDTO dto){
        List<StorageRecordBO> bos = storageRecordMapper.queryByShid(dto.getShid());
        return ResultVO.ok().setData(StorageRecordVO.convert(bos));
    }

}
