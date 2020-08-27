package com.smartindustry.storage.service.impl;

import com.github.pagehelper.Page;
import com.google.gson.internal.$Gson$Types;
import com.smartindustry.common.bo.im.MaterialInventoryBO;
import com.smartindustry.common.bo.si.PrintLabelBO;
import com.smartindustry.common.bo.sm.*;
import com.smartindustry.common.constant.ResultConstant;
import com.smartindustry.common.mapper.im.MaterialInventoryMapper;
import com.smartindustry.common.mapper.si.PrintLabelMapper;
import com.smartindustry.common.mapper.si.LocationMapper;
import com.smartindustry.common.mapper.si.StorageLabelMapper;
import com.smartindustry.common.mapper.sm.*;
import com.smartindustry.common.pojo.am.UserPO;
import com.smartindustry.common.pojo.im.MaterialInventoryPO;
import com.smartindustry.common.pojo.si.LocationPO;
import com.smartindustry.common.pojo.si.PrintLabelPO;
import com.smartindustry.common.pojo.si.StorageLabelPO;
import com.smartindustry.common.pojo.sm.*;
import com.smartindustry.common.security.service.TokenService;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.util.ServletUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.constant.ReceiptConstant;
import com.smartindustry.storage.dto.OperateDTO;
import com.smartindustry.storage.dto.StorageDetailDTO;
import com.smartindustry.storage.dto.StorageGroupDTO;
import com.smartindustry.storage.service.IMaterialStorageService;
import com.smartindustry.storage.vo.RecordVO;
import com.smartindustry.storage.vo.StorageDetailVO;
import com.smartindustry.storage.vo.StorageLabelVO;
import com.smartindustry.storage.vo.StoragePageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

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
    private StorageMapper storageMapper;
    @Autowired
    private LocationMapper locationMapper;
    @Autowired
    private PrintLabelMapper printLabelMapper;
    @Autowired
    private ReceiptLabelMapper receiptLabelMapper;
    @Autowired
    private ReceiptBodyMapper receiptBodyMapper;
    @Autowired
    private StorageRecordMapper recordMapper;
    @Autowired
    private StorageGroupMapper storageGroupMapper;
    @Autowired
    private StorageDetailMapper storageDetailMapper;
    @Autowired
    private StorageLabelMapper storageLabelMapper;
    @Autowired
    private MaterialInventoryMapper materialInventoryMapper;
    @Autowired
    TokenService tokenService;

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData) {
        Page<StorageBO> page = PageQueryUtil.startPage(reqData);
        List<StorageBO> bos = storageMapper.pageQuery(reqData);

        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), StoragePageVO.convert(bos)));
    }

    @Override
    public ResultVO pageQueryOther(Map<String, Object> reqData){
        Page<StorageBO> page = PageQueryUtil.startPage(reqData);
        List<StorageBO> bos = storageMapper.pageQueryOther(reqData);

        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), StoragePageVO.convert(bos)));
    }

    @Override
    public ResultVO location(@RequestBody OperateDTO dto) {
        LocationPO locationPO = locationMapper.queryByLno(dto.getLno());
        if (null == locationPO) {
            return new ResultVO(1002);
        }

        return ResultVO.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO label(StorageGroupDTO dto) {
        StorageBO storageBO = storageMapper.queryReceiptBySid(dto.getSid());
        if (null == storageBO) {
            return new ResultVO(1002);
        }

        PrintLabelBO bo = printLabelMapper.queryByRbidAndPid(storageBO.getReceiptBodyId(), dto.getPid());
        if (null == bo) {
            return new ResultVO(1002);
        }
        if (bo.getDr() == 2) {
            return new ResultVO(1006);
        }

        StorageDetailPO storageDetailPO = storageDetailMapper.queryByPlId(bo.getPrintLabelId());
        if (null != storageDetailPO) {
            // 标签已使用
            return new ResultVO(1004);
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

        if (null != dto.getSgid()) {
            StorageGroupPO storageGroupPO = storageGroupMapper.selectByPrimaryKey(dto.getSgid());
            if (null != storageGroupPO.getLocationId()) {
                // 打印标签
                PrintLabelPO printLabelPO = printLabelMapper.selectByPrimaryKey(bo.getPrintLabelId());
                List<Long> plids = new ArrayList<>(1);
                plids.add(printLabelPO.getPrintLabelId());
                printLabelMapper.updateLidByIds(storageGroupPO.getLocationId(), plids);
                receiptLabelMapper.updateSidByPlids(dto.getSid(), plids);
                // 入库单
                storageBO.setStoredNum(storageBO.getStoredNum() + printLabelPO.getNum());
                storageMapper.updateByPrimaryKey(storageBO);
                // 收料单
                ReceiptBodyPO receiptBodyPO = receiptBodyMapper.selectByPrimaryKey(dto.getRbid());
                receiptBodyPO.setStockNum(receiptBodyPO.getStockNum() + printLabelPO.getNum());
                receiptBodyMapper.updateByPrimaryKey(receiptBodyPO);
            }
        }

        return ResultVO.ok().setData(StorageLabelVO.convert(bo, dto.getSgid()));
    }

    @Override
    public ResultVO edit(StorageDetailDTO dto) {
        StorageDetailPO storageDetailPO = storageDetailMapper.selectByPrimaryKey(dto.getSdid());
        if (null == storageDetailPO) {
            return new ResultVO(1002);
        }
        StorageGroupPO storageGroupPO = storageGroupMapper.selectByPrimaryKey(dto.getSgid());
        if (null == storageGroupPO) {
            return new ResultVO(1002);
        }
        if (!StringUtils.isEmpty(storageGroupPO.getLocationId())) {
            // 修改入库单及打印标签
            StorageBO storageBO = storageMapper.queryReceiptBySid(dto.getSid());
            if (null == storageBO) {
                return new ResultVO(1002);
            }
            PrintLabelPO oldLabelPO = printLabelMapper.selectByPrimaryKey(storageDetailPO.getPrintLabelId());
            if (null == oldLabelPO) {
                return new ResultVO(1002);
            }
            ReceiptBodyPO receiptBodyPO = receiptBodyMapper.selectByPrimaryKey(storageBO.getReceiptBodyId());
            if (null == receiptBodyPO) {
                return new ResultVO(1002);
            }

            // 入库单
            PrintLabelPO newLabelPO = printLabelMapper.selectByPrimaryKey(dto.getPlid());
            if (null == newLabelPO) {
                return new ResultVO(1002);
            }
            storageBO.setStoredNum(storageBO.getStoredNum() + newLabelPO.getNum() - oldLabelPO.getNum());
            storageMapper.updateByPrimaryKey(storageBO);

            // 收料单
            receiptBodyPO.setStockNum(receiptBodyPO.getStockNum() + newLabelPO.getNum() - oldLabelPO.getNum());
            receiptBodyMapper.updateByPrimaryKey(receiptBodyPO);

            // 新打印标签
            List<Long> newPlids = new ArrayList<>(1);
            newPlids.add(newLabelPO.getPrintLabelId());
            printLabelMapper.updateLidByIds(storageGroupPO.getLocationId(), newPlids);
            receiptLabelMapper.updateSidByPlids(dto.getSid(), newPlids);
            // 收料单标签
            ReceiptLabelPO rlPO = receiptLabelMapper.queryByPrintLabelId(newLabelPO.getPrintLabelId());
            rlPO.setStorageId(dto.getSid());
            receiptLabelMapper.updateByPrimaryKey(rlPO);

            // 旧打印标签
            // 旧标签
            List<Long> oldPlids = new ArrayList<>(1);
            oldPlids.add(oldLabelPO.getPrintLabelId());
            printLabelMapper.updateLidByIds(null, oldPlids);
            receiptLabelMapper.updateSidByPlids(null, oldPlids);
        }

        // 修改入库详情
        storageDetailPO.setPrintLabelId(dto.getPlid());
        storageDetailMapper.updateByPrimaryKey(storageDetailPO);

        return ResultVO.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO delete(StorageDetailDTO dto) {
        StorageGroupPO storageGroupPO = storageGroupMapper.selectByPrimaryKey(dto.getSgid());
        if (null == storageGroupPO) {
            return new ResultVO(1002);
        }
        StorageDetailPO storageDetailPO = storageDetailMapper.selectByPrimaryKey(dto.getSdid());
        if (null == storageDetailPO) {
            return new ResultVO(1002);
        }

        if (null != storageGroupPO.getLocationId()) {
            // 修改入库单及打印标签
            StorageBO storageBO = storageMapper.queryReceiptBySid(dto.getSid());
            if (null == storageBO) {
                return new ResultVO(1002);
            }
            ReceiptBodyPO receiptBodyPO = receiptBodyMapper.selectByPrimaryKey(storageBO.getReceiptBodyId());
            if (null == receiptBodyPO) {
                return new ResultVO(1002);
            }
            PrintLabelPO oldLabelPO = printLabelMapper.selectByPrimaryKey(storageDetailPO.getPrintLabelId());
            if (null == oldLabelPO) {
                return new ResultVO(1002);
            }

            // 入库单
            int storedNum = storageBO.getStoredNum() - oldLabelPO.getNum();
            storageBO.setStoredNum(storedNum);
            if (storedNum == 0) {
                storageBO.setStatus(ReceiptConstant.MATERIAL_STORAGE_PENDING);
            }
            storageMapper.updateByPrimaryKey(storageBO);

            // 收料单
            receiptBodyPO.setStockNum(receiptBodyPO.getStockNum() - oldLabelPO.getNum());
            receiptBodyMapper.updateByPrimaryKey(receiptBodyPO);

            // 旧标签
            List<Long> plids = new ArrayList<>(1);
            plids.add(oldLabelPO.getPrintLabelId());
            printLabelMapper.updateLidByIds(null, plids);
            receiptLabelMapper.updateSidByPlids(null, plids);
        }

        // 删除入库详细
        storageDetailMapper.deleteByPrimaryKey(dto.getSdid());

        return ResultVO.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO save(StorageGroupDTO dto) {
        LocationPO locationPO = locationMapper.queryByLno(dto.getLno());
        if (null == locationPO) {
            return new ResultVO(1002);
        }
        StoragePO storagePO = storageMapper.selectByPrimaryKey(dto.getSid());
        if (null == storagePO) {
            return new ResultVO(1002);
        }
        ReceiptBodyPO receiptBodyPO = receiptBodyMapper.selectByPrimaryKey(dto.getRbid());
        if (null == receiptBodyPO) {
            return new ResultVO(1002);
        }
        StorageGroupPO storageGroupPO = storageGroupMapper.selectByPrimaryKey(dto.getSgid());
        if (null == storageGroupPO) {
            return new ResultVO(1002);
        }
        if (StringUtils.isEmpty(dto.getLno())) {
            return new ResultVO(1001);
        }
        if (locationPO.getLocationId().equals(storageGroupPO.getLocationId())) {
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
        printLabelMapper.updateLidByIds(locationPO.getLocationId(), plIds);
        receiptLabelMapper.updateSidByPlids(dto.getSid(), plIds);

        if (null == storageGroupPO.getLocationId()) {
            // 入库单
            storagePO.setStoredNum(storagePO.getStoredNum() + num);
            storagePO.setStatus(ReceiptConstant.MATERIAL_STORAGE_BEING);
            storageMapper.updateByPrimaryKey(storagePO);

            // 收料单
            receiptBodyPO.setStockNum(receiptBodyPO.getStockNum() + num);
            receiptBodyMapper.updateByPrimaryKey(receiptBodyPO);
        }

        // 入库分组
        storageGroupPO.setLocationId(locationPO.getLocationId());
        storageGroupMapper.updateByPrimaryKey(storageGroupPO);

        return ResultVO.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO storage(@RequestBody OperateDTO dto) {
        UserPO user = tokenService.getLoginUser();
        StorageBO storageBO = storageMapper.queryReceiptBySid(dto.getSid());
        if (null == storageBO) {
            return new ResultVO(1002);
        }
        if (storageBO.getPendingNum() > storageBO.getStoredNum()) {
            return new ResultVO(1005);
        }
        if (!ReceiptConstant.MATERIAL_STORAGE_BEING.equals(storageBO.getStatus())) {
            return new ResultVO(1003);
        }
        ReceiptBodyPO receiptBodyPO = receiptBodyMapper.queryByBodyId(storageBO.getReceiptBodyId());
        if (null == receiptBodyPO) {
            return new ResultVO(1002);
        }

        // 入库单
        storageBO.setStorageTime(new Date());
        storageBO.setStatus(ReceiptConstant.MATERIAL_STORAGE_FINISH);
        storageMapper.updateByPrimaryKey(storageBO);

        // 收料单
        receiptBodyPO.setStatus(ReceiptConstant.RECEIPT_STORAGE_FINISH);
        receiptBodyMapper.updateByPrimaryKey(receiptBodyPO);

        // 入库标签（出库管理、库内信息使用）
        List<StorageGroupBO> storageGroupBOs = storageGroupMapper.queryBySid(storageBO.getStorageId());
        List<StorageLabelPO> storageLabelPOs = new ArrayList<>();
        for (StorageGroupBO groupBO : storageGroupBOs) {
            for (StorageDetailBO detailBO : groupBO.getDetail()) {
                StorageLabelPO po = new StorageLabelPO();
                po.setLocationId(groupBO.getLocationId());
                po.setPrintLabelId(detailBO.getPrintLabelId());
                po.setMaterialId(detailBO.getMaterialId());
                po.setPackageId(detailBO.getPackageId());
                po.setSourceNo(receiptBodyPO.getSourceNo());
                po.setSourceType(receiptBodyPO.getSourceType());
                po.setType(storageBO.getType());
                po.setStorageNum(detailBO.getNum());
                po.setStorageTime(storageBO.getStorageTime());
                po.setStatus((byte) 1);
                if (!ReceiptConstant.STORAGE_TYPE_GOOD.equals(storageBO.getType())) {
                    // 不良锁定
                    po.setMaterialLockId(1L);
                }
                storageLabelPOs.add(po);
            }
        }
        storageLabelMapper.batchInsert(storageLabelPOs);

        // 物料库存信息
        MaterialInventoryBO materialInventoryBO = materialInventoryMapper.queryByMid(receiptBodyPO.getMaterialId());
        if (null != materialInventoryBO) {
            MaterialInventoryPO updateInventoryPO = new MaterialInventoryPO();
            updateInventoryPO.setStorageNum(storageBO.getStoredNum());
            if (ReceiptConstant.STORAGE_TYPE_GOOD.equals(storageBO.getType())) {
                // 良品
                if (null != receiptBodyPO.getOrderTotal() && receiptBodyPO.getOrderTotal() - storageBO.getStoredNum() > 0) {
                    updateInventoryPO.setWayNum(receiptBodyPO.getOrderTotal() - storageBO.getStoredNum());
                }
            } else {
                // 不良
                updateInventoryPO.setLockNum(storageBO.getStoredNum());
            }
            materialInventoryMapper.updateByPrimaryKey(materialInventoryBO.updatePO(updateInventoryPO));
        }

        // 操作记录
        recordMapper.insert(new StorageRecordPO(dto.getSid(), storageBO.getStorageId(), user.getUserId(), user.getName(), ReceiptConstant.RECORD_TYPE_STORAGE_CONFIRM, ReceiptConstant.RECEIPT_MATERIAL_STORAGE));

        return ResultVO.ok();
    }

    @Override
    public ResultVO detail(@RequestBody OperateDTO dto) {
        StorageBO storageBO = storageMapper.queryReceiptBySid(dto.getSid());
        if (null == storageBO) {
            return new ResultVO(1002);
        }
        ReceiptBodyBO receiptBodyBO = receiptBodyMapper.queryByBodyId(storageBO.getReceiptBodyId());
        if (null == receiptBodyBO) {
            return new ResultVO(1002);
        }

        List<StorageGroupBO> storageGroupBOs = storageGroupMapper.queryBySid(storageBO.getStorageId());

        return ResultVO.ok().setData(StorageDetailVO.convert(storageBO, receiptBodyBO, storageGroupBOs));
    }

    @Override
    public ResultVO record(@RequestBody OperateDTO dto) {
        Map<String, Object> res = new HashMap<>();
        List<StorageRecordPO> recordPOs = recordMapper.queryBySid(dto.getSid());
        res.put(ResultConstant.OPERATE_RECORD, RecordVO.convert(recordPOs));
        return ResultVO.ok().setData(res);
    }
}
