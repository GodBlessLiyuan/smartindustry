package com.smartindustry.storage.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.smartindustry.common.bo.MaterialStorageBO;
import com.smartindustry.common.bo.PrintLabelBO;
import com.smartindustry.common.bo.StorageDetailBO;
import com.smartindustry.common.mapper.*;
import com.smartindustry.common.pojo.*;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.constant.ReceiptConstant;
import com.smartindustry.storage.dto.StorageDetailDTO;
import com.smartindustry.storage.dto.StorageGroupDTO;
import com.smartindustry.storage.service.IMaterialStorageService;
import com.smartindustry.storage.vo.StorageLabelVO;
import com.smartindustry.storage.vo.StoragePageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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
    @Autowired
    private StorageGroupMapper storageGroupMapper;
    @Autowired
    private StorageDetailMapper storageDetailMapper;

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO label(StorageGroupDTO dto) {
        MaterialStoragePO materialStoragePO = materialStorageMapper.selectByPrimaryKey(dto.getSid());
        if (null == materialStoragePO) {
            return new ResultVO(2000);
        }

        PrintLabelBO bo = printLabelMapper.queryByRbidAndPid(materialStoragePO.getReceiptBodyId(), dto.getPid());
        if (null == bo) {
            return new ResultVO(2000);
        }

        if (null == dto.getSgid()) {
            // 新增物料入库组
            StorageGroupPO groupPO = new StorageGroupPO();
            groupPO.setStorageId(dto.getSid());
            storageGroupMapper.insert(groupPO);
            dto.setSgid(groupPO.getStorageGroupId());
        }

        // 新增物料入库详情
        StorageDetailPO detailPO = new StorageDetailPO();
        detailPO.setStorageGroupId(dto.getSgid());
        detailPO.setPrintLabelId(bo.getPrintLabelId());
        storageDetailMapper.insert(detailPO);

        if (null != materialStoragePO.getStorageNo()) {
            // 打印标签
            PrintLabelPO printLabelPO = printLabelMapper.selectByPrimaryKey(bo.getPrintLabelId());
            printLabelPO.setLocationNo(materialStoragePO.getStorageNo());
            printLabelPO.setStorageId(dto.getSid());
            printLabelMapper.updateByPrimaryKey(printLabelPO);
            // 入库单
            materialStoragePO.setStoredNum(materialStoragePO.getStoredNum() + printLabelPO.getNum());
            materialStorageMapper.updateByPrimaryKey(materialStoragePO);
            // 收料单
            ReceiptBodyPO receiptBodyPO = receiptBodyMapper.selectByPrimaryKey(dto.getRbid());
            receiptBodyPO.setStockNum(receiptBodyPO.getStockNum() + printLabelPO.getNum());
            receiptBodyMapper.updateByPrimaryKey(receiptBodyPO);
        }

        return ResultVO.ok().setData(StorageLabelVO.convert(bo, dto.getSgid()));
    }

    @Override
    public ResultVO edit(StorageDetailDTO dto) {
        StorageDetailPO storageDetailPO = storageDetailMapper.selectByPrimaryKey(dto.getSdid());
        if (null == storageDetailPO) {
            return new ResultVO(2000);
        }
        StorageGroupPO storageGroupPO = storageGroupMapper.selectByPrimaryKey(dto.getSgid());
        if (null == storageGroupPO) {
            return new ResultVO(2000);
        }
        if (!StringUtils.isEmpty(storageGroupPO.getLocationNo())) {
            // 修改入库单及打印标签
            PrintLabelPO oldLabelPO = printLabelMapper.selectByPrimaryKey(storageDetailPO.getPrintLabelId());
            if (null == oldLabelPO) {
                return new ResultVO(2000);
            }
            MaterialStoragePO materialStoragePO = materialStorageMapper.selectByPrimaryKey(dto.getSid());
            if (null == materialStoragePO) {
                return new ResultVO(2000);
            }

            // 入库单
            PrintLabelPO newLabelPO = printLabelMapper.selectByPrimaryKey(dto.getPlid());
            if (null == newLabelPO) {
                return new ResultVO(2000);
            }
            materialStoragePO.setStoredNum(materialStoragePO.getStoredNum() + newLabelPO.getNum() - oldLabelPO.getNum());
            materialStorageMapper.updateByPrimaryKey(materialStoragePO);

            // 新打印标签
            newLabelPO.setStorageId(dto.getSid());
            newLabelPO.setLocationNo(storageGroupPO.getLocationNo());
            printLabelMapper.updateByPrimaryKey(newLabelPO);

            // 旧打印标签
            oldLabelPO.setLocationNo(null);
            oldLabelPO.setStorageId(null);
            printLabelMapper.updateByPrimaryKey(oldLabelPO);
        }

        // 修改入库详情
        storageDetailPO.setPrintLabelId(dto.getPlid());
        storageDetailMapper.updateByPrimaryKey(storageDetailPO);

        return ResultVO.ok();
    }

    @Override
    public ResultVO delete(StorageDetailDTO dto) {
        StorageGroupPO storageGroupPO = storageGroupMapper.selectByPrimaryKey(dto.getSgid());
        if (null == storageGroupPO) {
            return new ResultVO(2000);
        }
        StorageDetailPO storageDetailPO = storageDetailMapper.selectByPrimaryKey(dto.getSdid());
        if (null == storageDetailPO) {
            return new ResultVO(2000);
        }

        if (!StringUtils.isEmpty(storageGroupPO.getLocationNo())) {
            // 修改入库单及打印标签
            MaterialStoragePO materialStoragePO = materialStorageMapper.selectByPrimaryKey(dto.getSid());
            if (null == materialStoragePO) {
                return new ResultVO(2000);
            }
            ReceiptBodyPO receiptBodyPO = receiptBodyMapper.selectByPrimaryKey(materialStoragePO.getReceiptBodyId());
            if (null == receiptBodyPO) {
                return new ResultVO(2000);
            }
            PrintLabelPO oldLabelPO = printLabelMapper.selectByPrimaryKey(storageDetailPO.getPrintLabelId());
            if (null == oldLabelPO) {
                return new ResultVO(2000);
            }

            // 入库单
            int storedNum = materialStoragePO.getStoredNum() - oldLabelPO.getNum();
            materialStoragePO.setStoredNum(storedNum);
            if (storedNum == 0) {
                materialStoragePO.setStorageTime(null);
                materialStoragePO.setStatus(ReceiptConstant.MATERIAL_STORAGE_PENDING);
            }
            materialStorageMapper.updateByPrimaryKey(materialStoragePO);

            // 收料单
            receiptBodyPO.setStockNum(receiptBodyPO.getStockNum() - oldLabelPO.getNum());
            receiptBodyMapper.updateByPrimaryKey(receiptBodyPO);

            // 旧打印标签
            oldLabelPO.setLocationNo(null);
            oldLabelPO.setStorageId(null);
            printLabelMapper.updateByPrimaryKey(oldLabelPO);
        }

        // 删除
        storageDetailMapper.deleteByPrimaryKey(dto.getSdid());

        return ResultVO.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO save(StorageGroupDTO dto) {
        MaterialStoragePO materialStoragePO = materialStorageMapper.selectByPrimaryKey(dto.getSid());
        if (null == materialStoragePO) {
            return new ResultVO(2000);
        }
        ReceiptBodyPO receiptBodyPO = receiptBodyMapper.selectByPrimaryKey(dto.getRbid());
        if (null == receiptBodyPO) {
            return new ResultVO(2000);
        }
        StorageGroupPO storageGroupPO = storageGroupMapper.selectByPrimaryKey(dto.getSgid());
        if (null == storageGroupPO) {
            return new ResultVO(2000);
        }
        if (StringUtils.isEmpty(dto.getLno())) {
            return new ResultVO(2000);
        }
        if (dto.getLno().equals(storageGroupPO.getLocationNo())) {
            return ResultVO.ok();
        }

        // 打印标签
        List<StorageDetailBO> storageDetailBOs = storageDetailMapper.queryByGroupId(storageGroupPO.getStorageGroupId());
        int num = 0;
        List<Long> plIds = new ArrayList<>(storageDetailBOs.size());
        for (StorageDetailBO bo : storageDetailBOs) {
            num += bo.getNum();
            plIds.add(bo.getPrintLabelId());
        }
        printLabelMapper.updateSidAndlnoByIds(dto.getSid(), dto.getLno(), plIds);

        if (null == storageGroupPO.getLocationNo()) {
            // 入库单
            materialStoragePO.setStoredNum(materialStoragePO.getStoredNum() + num);
            materialStoragePO.setStatus(ReceiptConstant.MATERIAL_STORAGE_BEING);
            materialStorageMapper.updateByPrimaryKey(materialStoragePO);

            // 收料单
            receiptBodyPO.setStockNum(receiptBodyPO.getStockNum() + num);
            receiptBodyMapper.updateByPrimaryKey(receiptBodyPO);
        }

        // 入库分组
        storageGroupPO.setLocationNo(dto.getLno());
        storageGroupMapper.updateByPrimaryKey(storageGroupPO);

        return ResultVO.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO storage(Long sid) {
        MaterialStoragePO materialStoragePO = materialStorageMapper.selectByPrimaryKey(sid);
        if (null == materialStoragePO) {
            return new ResultVO(2000);
        }
        if (!materialStoragePO.getPendingNum().equals(materialStoragePO.getStoredNum())) {
            return new ResultVO(2000);
        }

        materialStoragePO.setStatus(ReceiptConstant.MATERIAL_STORAGE_FINISH);
        materialStorageMapper.updateByPrimaryKey(materialStoragePO);

        // 操作记录
        recordMapper.insert(new RecordPO(sid, materialStoragePO.getStorageId(), 1L, "夏慧", ReceiptConstant.RECORD_TYPE_STORAGE_CONFIRM, ReceiptConstant.RECEIPT_MATERIAL_STORAGE));

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
