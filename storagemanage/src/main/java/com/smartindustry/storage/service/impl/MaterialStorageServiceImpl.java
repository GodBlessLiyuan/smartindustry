package com.smartindustry.storage.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.common.bo.im.MaterialInventoryBO;
import com.smartindustry.common.bo.om.PickBodyBO;
import com.smartindustry.common.bo.si.PrintLabelBO;
import com.smartindustry.common.bo.sm.*;
import com.smartindustry.common.constant.ResultConstant;
import com.smartindustry.common.mapper.em.TransferHeadMapper;
import com.smartindustry.common.mapper.im.MaterialInventoryMapper;
import com.smartindustry.common.mapper.si.LocationMapper;
import com.smartindustry.common.mapper.si.PrintLabelMapper;
import com.smartindustry.common.mapper.si.StorageLabelMapper;
import com.smartindustry.common.mapper.si.WarehouseMapper;
import com.smartindustry.common.mapper.sm.*;
import com.smartindustry.common.pojo.am.UserPO;
import com.smartindustry.common.pojo.em.TransferHeadPO;
import com.smartindustry.common.pojo.im.MaterialInventoryPO;
import com.smartindustry.common.pojo.si.LocationPO;
import com.smartindustry.common.pojo.si.PrintLabelPO;
import com.smartindustry.common.pojo.si.StorageLabelPO;
import com.smartindustry.common.pojo.sm.*;
import com.smartindustry.common.security.service.TokenService;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.constant.ReceiptConstant;
import com.smartindustry.storage.dto.OperateDTO;
import com.smartindustry.storage.dto.StorageDetailDTO;
import com.smartindustry.storage.dto.StorageGroupDTO;
import com.smartindustry.storage.service.IMaterialStorageService;
import com.smartindustry.storage.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    TransferHeadMapper transferHeadMapper;

    @Autowired
    private WarehouseMapper warehouseMapper;

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData) {
        Page<Long> page = PageQueryUtil.startPage(reqData);
        List<Long> sids = storageMapper.pageQuery(reqData);

        List<StorageBO> bos = new ArrayList<>();
        if (null != sids && sids.size() > 0) {
            bos = storageMapper.queryBySids(sids);
        }

        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), StoragePageVO.convert(bos)));
    }

    @Override
    public ResultVO pageQueryOther(Map<String, Object> reqData) {
        Page<StorageBO> page = PageQueryUtil.startPage(reqData);
        List<StorageBO> bos = storageMapper.pageQueryOther(reqData);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), StoragePageVO.convert(bos)));
    }

    @Override
    public ResultVO queryBySid(OperateDTO dto) {
        StorageBO bo = storageMapper.queryBySid(dto.getSid());
        return ResultVO.ok().setData(StoragePageVO.convert(bo));
    }

    @Override
    public ResultVO queryInfo(OperateDTO dto) {
        List<PickBodyBO> bos = storageMapper.queryInfo(dto.getSid());
        return ResultVO.ok().setData(PickBodyVO.convert(bos));
    }

    @Override
    public ResultVO queryPidInfo(Map<String, Object> reqData) {
        Page<PrintLabelBO> page = PageQueryUtil.startPage(reqData);
        List<PrintLabelBO> bos = storageMapper.queryPidInfo(reqData);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), PrintLabelVO.convertBO(bos)));
    }

    /**
     * 其他入库单的扫码入库
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO storageScan(OperateDTO dto) {
        //判断当前扫码入库pid是否存在
        PrintLabelPO po1 = printLabelMapper.queryByPid(dto.getPid());
        if (po1 == null) {
            return new ResultVO(1002);
        }
        //判断当前的扫码pid是否已经被使用
        StorageDetailPO storageDetailPO = storageDetailMapper.queryByPlId(po1.getPrintLabelId());
        if (null != storageDetailPO) {
            // 标签已使用
            return new ResultVO(1004);
        }
        //其它入库单的扫码入库,首先找到所有拣货时的pid列表
        List<String> pids = storageMapper.queryRelatePid(dto.getSid());
        if (!pids.contains(dto.getPid())) {
            //当前pid不符合条件
            return new ResultVO(1005);
        }
        if (null == dto.getSgid()) {
            // 新增物料入库组
            StorageGroupPO groupPO = new StorageGroupPO();
            groupPO.setStorageId(dto.getSid());
            storageGroupMapper.insert(groupPO);
            dto.setSgid(groupPO.getStorageGroupId());
        }

        // 新增入库详情表
        StorageDetailPO detailPO = new StorageDetailPO();
        detailPO.setStorageGroupId(dto.getSgid());
        detailPO.setPrintLabelId(po1.getPrintLabelId());
        storageDetailMapper.insert(detailPO);
        //判断当前扫码pid号是否在列表中，若是则扫码成功，若不是提示不属于相关pid
        //更新入库表已入库数和状态,更新库位标签表，入库详情组表等
        if (null != dto.getSgid()) {
            StorageGroupPO storageGroupPO = storageGroupMapper.selectByPrimaryKey(dto.getSgid());
            if (null != storageGroupPO.getLocationId()) {
                StoragePO po = storageMapper.selectByPrimaryKey(dto.getSid());
                po.setStoredNum(po.getStoredNum() + po1.getNum());
                po.setStatus(ReceiptConstant.MATERIAL_STORAGE_BEING);
                storageMapper.updateByPrimaryKey(po);

                // 打印标签
                PrintLabelPO printLabelPO = printLabelMapper.selectByPrimaryKey(po1.getPrintLabelId());
                List<Long> plids = new ArrayList<>(1);
                plids.add(printLabelPO.getPrintLabelId());
                printLabelMapper.updateLidByIds(storageGroupPO.getLocationId(), plids);
            }
        }

        //扫码成功之后,查询所有目前的扫码进库状态值
        PrintLabelBO bo = storageMapper.queryPrint(dto.getSid(), dto.getPid());
        Long id = storageGroupMapper.queryGroup(dto.getSid());
        return ResultVO.ok().setData(StorageLabelVO.convert(bo, id));
    }

    /**
     * 其他入库单的删除
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO storageDelete(StorageDetailDTO dto) {
        //查看入库详情表是否存在
        StorageGroupPO storageGroupPO = storageGroupMapper.selectByPrimaryKey(dto.getSgid());
        if (null == storageGroupPO) {
            return new ResultVO(1002);
        }
        //查看入库详情表是否存在数据
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

    /**
     * 其他入库单的保存
     *
     * @param dto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO storageSave(StorageGroupDTO dto) {
        LocationPO locationPO = locationMapper.queryByLno(dto.getLno());
        if (null == locationPO) {
            return new ResultVO(1002);
        }
        StoragePO storagePO = storageMapper.selectByPrimaryKey(dto.getSid());
        if (null == storagePO) {
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

        if (null == storageGroupPO.getLocationId()) {
            // 入库单
            storagePO.setStoredNum(storagePO.getStoredNum() + num);
            storagePO.setStatus(ReceiptConstant.MATERIAL_STORAGE_BEING);
            storageMapper.updateByPrimaryKey(storagePO);
        }
        // 入库分组
        storageGroupPO.setLocationId(locationPO.getLocationId());
        storageGroupMapper.updateByPrimaryKey(storageGroupPO);
        return ResultVO.ok();
    }

    /**
     * 其他入库单的扫码同意入库
     *
     * @param dto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO storageAgree(OperateDTO dto) {
        UserPO user = tokenService.getLoginUser();
        StoragePO storagePO = storageMapper.selectByPrimaryKey(dto.getSid());
        if (null == storagePO) {
            return new ResultVO(1002);
        }
        if (storagePO.getPendingNum() > storagePO.getStoredNum()) {
            return new ResultVO(1005);
        }
        if (!ReceiptConstant.MATERIAL_STORAGE_BEING.equals(storagePO.getStatus())) {
            return new ResultVO(1003);
        }

        TransferHeadPO transferHeadPO = transferHeadMapper.queryBySid(dto.getSid());
        // 入库单
        storagePO.setStorageTime(new Date());
        storagePO.setStatus(ReceiptConstant.MATERIAL_STORAGE_FINISH);
        storageMapper.updateByPrimaryKey(storagePO);
        // 入库标签（出库管理、库内信息使用）
        List<StorageGroupBO> storageGroupBOs = storageGroupMapper.queryBySid(storagePO.getStorageId());
        List<StorageLabelPO> storageLabelPOs = new ArrayList<>();
        for (StorageGroupBO groupBO : storageGroupBOs) {
            for (StorageDetailBO detailBO : groupBO.getDetail()) {
                StorageLabelPO po = new StorageLabelPO();
                po.setLocationId(groupBO.getLocationId());
                po.setPrintLabelId(detailBO.getPrintLabelId());
                po.setMaterialId(detailBO.getMaterialId());
                po.setPackageId(detailBO.getPackageId());
                po.setSourceNo(transferHeadPO.getTransferNo());
                po.setSourceType(transferHeadPO.getTransferType());
                po.setType(storagePO.getType());
                po.setStorageNum(detailBO.getNum());
                po.setStorageTime(storagePO.getStorageTime());
                po.setStatus((byte) 1);
                if (!ReceiptConstant.STORAGE_TYPE_GOOD.equals(storagePO.getType())) {
                    // 不良锁定
                    po.setMaterialLockId(1L);
                }
                storageLabelPOs.add(po);
            }
        }
        storageLabelMapper.batchInsert(storageLabelPOs);

        // 物料库存信息

        // 操作记录
        recordMapper.insert(new StorageRecordPO(dto.getSid(), storagePO.getStorageId(), user.getUserId(), user.getName(), ReceiptConstant.RECORD_TYPE_STORAGE_CONFIRM, ReceiptConstant.RECEIPT_MATERIAL_STORAGE));

        return ResultVO.ok();
    }

    /**
     * 其他入库单的详细信息查询
     *
     * @param dto
     * @return
     */
    @Override
    public ResultVO storageDetail(OperateDTO dto) {
        StoragePO storagePO = storageMapper.selectByPrimaryKey(dto.getSid());
        if (null == storagePO) {
            return new ResultVO(1002);
        }
        List<StorageGroupBO> storageGroupBOs = storageGroupMapper.queryBySid(storagePO.getStorageId());
        ReceiptBodyBO receiptBodyBO = new ReceiptBodyBO();
        StorageBO storageBO = new StorageBO();
        BeanUtils.copyProperties(storagePO, storageBO);
        return ResultVO.ok().setData(StorageDetailVO.convert(storageBO, receiptBodyBO, storageGroupBOs));
    }


    @Override
    public ResultVO agreeStorage(OperateDTO dto) {
        UserPO user = tokenService.getLoginUser();
        StoragePO po = storageMapper.selectByPrimaryKey(dto.getSid());
        po.setStatus(ReceiptConstant.MATERIAL_STORAGE_FINISH);
        po.setStoredNum(po.getPendingNum());
        storageMapper.updateByPrimaryKey(po);
        // 操作记录
        recordMapper.insert(new StorageRecordPO(dto.getSid(), po.getStorageId(), user.getUserId(), user.getName(), ReceiptConstant.RECORD_TYPE_STORAGE_CONFIRM, ReceiptConstant.RECEIPT_MATERIAL_STORAGE));
        return ResultVO.ok();
    }

    @Override
    public ResultVO location(OperateDTO dto) {
        if (StringUtils.isEmpty(dto.getLno())) {
            return new ResultVO(1001);
        }
        LocationPO locationPO = locationMapper.queryByLnoAndWhid(dto.getLno(), dto.getWhid());
        if (locationPO == null) {
            return new ResultVO(1002);
        }
        return ResultVO.ok().setData(LocationVO.convert(locationPO));

    }

    /**
     * 查询入库单的已入库情况查询
     *
     * @param dto
     * @return
     */
    @Override
    public ResultVO storageDetail4Sid(OperateDTO dto) {
        if (dto.getSid() == null) {
            return new ResultVO(1001);
        }
        List<StorageGroupDetailBO> storageGroupBOs = storageGroupMapper.queryStorageDetail(dto.getSid(), dto.getSgid());
        Map<String, List<StorageGroupDetailBO>> groupMap = storageGroupBOs.stream().collect(Collectors.toMap(
                p -> p.getMaterialName() + "_" + p.getMaterialNo(),
                p -> {
                    List<StorageGroupDetailBO> bs = new ArrayList<>();
                    bs.add(p);
                    return bs;
                },
                (List<StorageGroupDetailBO> values1, List<StorageGroupDetailBO> values2) -> {
                    values1.addAll(values2);
                    return values1;
                }
        ));
        List<StorageGroupDetailBO> bos = new ArrayList<>();
        for (String key : groupMap.keySet()) {
            String[] keys = key.split("_");
            StorageGroupDetailBO dbo = new StorageGroupDetailBO();
            dbo.setMaterialName(keys[0]);
            dbo.setMaterialNo(keys[1]);
            List<StorageDetailBO> details = new ArrayList<>();
            Integer num = 0;
            for (StorageGroupDetailBO bo : groupMap.get(key)) {
                details.addAll(bo.getDetail());
                num += bo.getDetail().stream().collect(Collectors.summingInt(StorageDetailBO::getNum));
            }

            Map<String, List<StorageDetailBO>> locationMap = details.stream().collect(Collectors.toMap(
                    p -> p.getWarehouseName() +"_"+ p.getLocationNo(),
                    p -> {
                        List<StorageDetailBO> bs = new ArrayList<>();
                        bs.add(p);
                        return bs;
                    },
                    (List<StorageDetailBO> values1, List<StorageDetailBO> values2) -> {
                        values1.addAll(values2);
                        return values1;
                    }
            ));
            List<StorageDetailBO> detail = new ArrayList<>();
            for (String locationKey: locationMap.keySet()) {
                String[] locationKeys = locationKey.split("_");
                StorageDetailBO sdbo = new StorageDetailBO();
                sdbo.setWarehouseName(locationKeys[0]);
                sdbo.setLocationNo(locationKeys[1]);
                sdbo.setLabels(locationMap.get(locationKey));
                detail.add(sdbo);
            }
            dbo.setDetail(detail);
            dbo.setNum(num);
            bos.add(dbo);
        }
        return ResultVO.ok().setData(StorageSimpleDetailVO.convert(bos));
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

        List<StorageGroupBO> unlocateBos = storageGroupBOs.stream().filter(StorageGroupBO -> StorageGroupBO.getLocationNo() == null).collect(Collectors.toList());

        //综合入库详情组表
        for (StorageGroupBO bo : storageGroupBOs) {
            //将所有的入库按照
            Map<String, List<StorageDetailBO>> map = bo.getDetail().stream().collect(Collectors.toMap(StorageDetailBO::getMaterialNo, p -> {
                        List<StorageDetailBO> bs = new ArrayList<>();
                        bs.add(p);
                        return bs;
                    }, (List<StorageDetailBO> values1, List<StorageDetailBO> values2) -> {
                        values1.addAll(values2);
                        return values1;
                    }
            ));
            List<StorageDetailBO> bos = new ArrayList<>(map.size());
            for (String materialNo : map.keySet()) {
                StorageDetailBO detailBO = map.get(materialNo).get(0);
                detailBO.setPackageId(null);
                detailBO.setNum(map.get(materialNo).stream().collect(Collectors.summingInt(StorageDetailBO::getNum)));
                bos.add(detailBO);
            }
            bo.setDetail(bos);
        }

        return ResultVO.ok().setData(StorageDetailVO.convert(storageBO, receiptBodyBO, storageGroupBOs, unlocateBos.isEmpty() ? null : unlocateBos));
    }

    @Override
    public ResultVO record(@RequestBody OperateDTO dto) {
        Map<String, Object> res = new HashMap<>();
        List<StorageRecordPO> recordPOs = recordMapper.queryBySid(dto.getSid());
        res.put(ResultConstant.OPERATE_RECORD, RecordVO.convert(recordPOs));
        return ResultVO.ok().setData(res);
    }

    /**
     * 当选择仓库发生变化时 清空所有已经入库的数据
     *
     * @param dto
     * @return
     */
    @Override
    public ResultVO changeWarehouse(OperateDTO dto) {
        if (dto.getSid() == null) {
            return new ResultVO(1001);
        }
        StorageBO storageBO = storageMapper.queryReceiptBySid(dto.getSid());
        if (null == storageBO) {
            return new ResultVO(1002);
        }
        /**
         * step 1 查找标签列表  去除仓位
         * step 2 查找收料单， 更新入库数量和状态,更新入库单的入库数量
         * step 3 查找所有的入库详情、 都需要进行删除
         * step 4 删除入库详情组
         */
        List<StorageGroupBO> storageGroupPOS = storageGroupMapper.queryBySid(dto.getSid());

        //step 1 查找标签列表  去除仓位
        ReceiptBodyPO rbPO = receiptBodyMapper.selectByPrimaryKey(storageBO.getReceiptBodyId());
        rbPO.setStockNum(0);
        rbPO.setStatus(ReceiptConstant.RECEIPT_MATERIAL_STORAGE);
        receiptBodyMapper.updateByPrimaryKey(rbPO);

        List<Long> sgIds = new ArrayList<>(storageGroupPOS.size());
        for(StorageGroupBO sgBO: storageGroupPOS) {
           sgIds.add(sgBO.getStorageGroupId());
        }

        List<StorageDetailBO> storageDetailBOS = storageDetailMapper.queryByGroupIds(sgIds);
        List<Long> plIds  = new ArrayList<>();
        for (StorageDetailBO sdBo: storageDetailBOS) {
            plIds.add(sdBo.getPrintLabelId());
        }
        printLabelMapper.updateLidByIds(null, plIds);
        //step2 查找收料单， 更新入库数量和状态, 更新收料单
        storageBO.setStatus(ReceiptConstant.MATERIAL_STORAGE_PENDING);
        storageBO.setStoredNum(0);
        storageMapper.updateByPrimaryKey(storageBO);

        rbPO.setStockNum(0);
        receiptBodyMapper.updateByPrimaryKey(rbPO);

        //step 3 查找所有的入库详情、 都需要进行删除
        storageDetailMapper.deleteBySid(dto.getSid());

        //step 4 删除入库详情组
        storageGroupMapper.batchDeleteByIds(sgIds);
        return ResultVO.ok();
    }

    /**
     * 通过入库详情组ID查询标签列表
     *
     * @param dto
     * @return
     */
    @Override
    public ResultVO queryBySgid(OperateDTO dto) {
        if (null == dto.getSgid()) {
            return new ResultVO(1001);
        }
        List<StorageDetailBO> storageDetailBOS = storageDetailMapper.queryByGroupId(dto.getSgid());
        return ResultVO.ok().setData(StorageDetailVO.convert(storageDetailBOS));
    }
}
