package com.smartindustry.storage.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.smartindustry.common.bo.MaterialStorageBO;
import com.smartindustry.common.bo.PrintLabelBO;
import com.smartindustry.common.mapper.*;
import com.smartindustry.common.pojo.*;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.constant.ReceiptConstant;
import com.smartindustry.storage.dto.MaterialStorageDTO;
import com.smartindustry.storage.service.IMaterialStorageService;
import com.smartindustry.storage.vo.StorageLabelVO;
import com.smartindustry.storage.vo.StoragePageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/6/23 11:41
 * @description: 物料入库
 * @version: 1.0
 */
@EnableTransactionManagement
@Service
public class MaterialStorageServiceImpl implements IMaterialStorageService {
    @Autowired
    private MaterialStorageMapper materialStorageMapper;
    @Autowired
    private StorageLocationMapper storageLocationMapper;
    @Autowired
    private PrintLabelMapper printLabelMapper;
    @Autowired
    private ReceiptBodyMapper receiptBodyMapper;
    @Autowired
    private RecordMapper recordMapper;

    @Override
    public ResultVO pageQuery(int pageNum, int pageSize, Map<String, Object> reqData) {
        Page<MaterialStorageBO> page = PageHelper.startPage(pageNum, pageSize);
        List<MaterialStorageBO> bos = materialStorageMapper.pageQuery(reqData);

        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), StoragePageVO.convert(bos)));
    }

    @Override
    public ResultVO location(String lno) {
        StorageLocationPO locationPO = storageLocationMapper.selectByPrimaryKey(lno);
        if (null == locationPO) {
            return new ResultVO(2000);
        }

        return ResultVO.ok();
    }

    @Override
    public ResultVO label(Long rbid, String pid) {
        PrintLabelBO bo = printLabelMapper.queryByRbidAndPid(rbid, pid);
        if (null == bo) {
            return new ResultVO(2000);
        }

        return ResultVO.ok().setData(StorageLabelVO.convert(bo));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO storage(MaterialStorageDTO dto) throws Exception {
        ReceiptBodyPO receiptBodyPO = receiptBodyMapper.selectByPrimaryKey(dto.getRbid());
        if (null == receiptBodyPO) {
            return new ResultVO(2000);
        }
        if (!ReceiptConstant.RECEIPT_MATERIAL_STORAGE.equals(receiptBodyPO.getStatus())) {
            return new ResultVO(2000);
        }
        MaterialStoragePO materialStoragePO = materialStorageMapper.selectByPrimaryKey(dto.getSid());
        if (null == materialStoragePO) {
            return new ResultVO(2000);
        }
        if (ReceiptConstant.MATERIAL_STORAGE_FINISH.equals(materialStoragePO.getStatus())) {
            return new ResultVO(2000);
        }

        int total = 0;
        for (MaterialStorageDTO.StorageDetailDTO detailDTO : dto.getDetail()) {
            // 更新打印标签
            List<PrintLabelPO> labelPOs = printLabelMapper.queryByIds(detailDTO.getPlids());
            if (labelPOs.size() != detailDTO.getPlids().size()) {
                throw new Exception("打印标签参数有误");
            }

            for (PrintLabelPO labelPO : labelPOs) {
                labelPO.setLocationNo(detailDTO.getLno());
                labelPO.setStorageId(materialStoragePO.getStorageId());
                total += labelPO.getNum();
            }

            printLabelMapper.batchUpdate(labelPOs);
        }

        // 更新入库单
        materialStoragePO.setStoredNum(materialStoragePO.getStoredNum() + total);
        materialStoragePO.setStorageTime(new Date());
        materialStoragePO.setStatus(materialStoragePO.getStoredNum() >= materialStoragePO.getPendingNum() ?
                ReceiptConstant.MATERIAL_STORAGE_FINISH : ReceiptConstant.MATERIAL_STORAGE_BEING);
        materialStorageMapper.updateByPrimaryKey(materialStoragePO);

        // 更新收料单
        receiptBodyPO.setStockNum(receiptBodyPO.getStockNum() + total);
        if (ReceiptConstant.MATERIAL_STORAGE_FINISH.equals(materialStoragePO.getStatus())) {
            receiptBodyPO.setStatus(ReceiptConstant.RECEIPT_STORAGE_FINISH);
        }
        receiptBodyMapper.updateByPrimaryKey(receiptBodyPO);

        // 操作记录
        recordMapper.insert(new RecordPO(dto.getRbid(), materialStoragePO.getStorageId(), 1L, "夏慧", ReceiptConstant.RECORD_TYPE_STORAGE_CONFIRM, ReceiptConstant.RECEIPT_MATERIAL_STORAGE));

        return ResultVO.ok();
    }

    @Override
    public ResultVO detail(Long sid) {
        MaterialStoragePO materialStoragePO = materialStorageMapper.selectByPrimaryKey(sid);
        if (null == materialStoragePO) {
            return new ResultVO(2000);
        }


        return null;
    }
}
