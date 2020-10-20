package com.smartindustry.storage.service.impl;

import com.smartindustry.common.bo.si.PrintLabelBO;
import com.smartindustry.common.bo.sm.ReceiptBodyBO;
import com.smartindustry.common.constant.ConfigConstant;
import com.smartindustry.common.constant.ModuleConstant;
import com.smartindustry.common.mapper.si.ConfigMapper;
import com.smartindustry.common.mapper.si.LabelRecordMapper;
import com.smartindustry.common.mapper.si.MaterialAttributeMapper;
import com.smartindustry.common.mapper.si.PrintLabelMapper;
import com.smartindustry.common.mapper.sm.*;
import com.smartindustry.common.pojo.am.UserPO;
import com.smartindustry.common.pojo.si.ConfigPO;
import com.smartindustry.common.pojo.si.LabelRecordPO;
import com.smartindustry.common.pojo.si.MaterialAttributePO;
import com.smartindustry.common.pojo.si.PrintLabelPO;
import com.smartindustry.common.pojo.sm.*;
import com.smartindustry.common.security.service.TokenService;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.constant.ReceiptConstant;
import com.smartindustry.storage.dto.LabelSplitDTO;
import com.smartindustry.storage.dto.OperateDTO;
import com.smartindustry.storage.dto.PrintLabelDTO;
import com.smartindustry.storage.service.ILabelManageService;
import com.smartindustry.storage.util.ReceiptNoUtil;
import com.smartindustry.storage.vo.PrintLabelVO;
import com.smartindustry.storage.vo.StorageSimpleDetailVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: xiahui
 * @date: Created in 2020/6/28 15:06
 * @description: 标签管理
 * @version: 1.0
 */
@EnableTransactionManagement
@Service
public class LabelManageServiceImpl implements ILabelManageService {

    @Autowired
    private PrintLabelMapper printLabelMapper;
    @Autowired
    private ReceiptLabelMapper receiptLabelMapper;
    @Autowired
    private ReceiptBodyMapper receiptBodyMapper;
    @Autowired
    private IqcDetectMapper iqcDetectMapper;
    @Autowired
    private QeDetectMapper qeDetectMapper;
    @Autowired
    private StorageRecordMapper recordMapper;
    @Autowired
    private LabelRecordMapper labelRecordMapper;
    @Autowired
    private ConfigMapper configMapper;
    @Autowired
    private StorageMapper storageMapper;
    @Autowired
    private MaterialAttributeMapper materialAttributeMapper;

    @Autowired
    private StorageDetailMapper storageDetailMapper;
    @Autowired
    TokenService tokenService;

    @Override
    public ResultVO query(@RequestBody OperateDTO dto) {
        List<PrintLabelPO> pos = printLabelMapper.queryByReceiptBodyId(dto.getRbid(), dto.getStatus());
        return ResultVO.ok().setData(PrintLabelVO.convert(pos));
    }

    @Override
    public ResultVO queryPid(@RequestBody OperateDTO dto) {
        PrintLabelBO bo = printLabelMapper.queryByRbidAndPid(dto.getRbid(), dto.getPid());
        if (null == bo) {
            return new ResultVO(1002);
        }
        if (bo.getDr() == 2) {
            return new ResultVO(1006);
        }
        return ResultVO.ok().setData(PrintLabelVO.convert(bo));
    }

    /**
     * 根据物料单ID 获取待入库物料列表
     *
     * @param dto
     * @return
     */
    @Override
    public ResultVO queryRbid(OperateDTO dto) {
        if (dto.getRbid() == null) {
            return new ResultVO(1001);
        }
        List<PrintLabelBO> bos = printLabelMapper.queryByRbid(dto.getRbid());
        return ResultVO.ok().setData(StorageSimpleDetailVO.convertLabel(bos));
    }

    /**
     * 根据调拨订单的调拨单编号查询 待入库物料列表
     *
     * @param dto
     * @return
     */
    @Override
    public ResultVO queryTSono(OperateDTO dto) {
        if (StringUtils.isEmpty(dto.getSono()) || dto.getSid() == null) {
            return new ResultVO(1001);
        }
        //根据sono 的所有标签列表
        List<PrintLabelBO> bos = printLabelMapper.queryByTSono(dto.getSono());
        //查询该入库单已经入库的标签ID
        List<Long> plids = storageDetailMapper.queryPlIdBySid(dto.getSid());
        List<PrintLabelBO> labels = bos.stream().filter(p -> !plids.contains(p.getPrintLabelId())).collect(Collectors.toList());
        return ResultVO.ok().setData(StorageSimpleDetailVO.convertLabel(labels));
    }

    @Override
    public ResultVO print(@RequestBody OperateDTO dto) {
        UserPO user = tokenService.getLoginUser();
        PrintLabelPO labelPO = printLabelMapper.queryByPidAndDr(dto.getPid(), (byte) 1);
        if (null == labelPO) {
            return new ResultVO(1002);
        }

        // 打印记录
        labelRecordMapper.insert(new LabelRecordPO(labelPO.getPrintLabelId(), user.getUserId(), user.getName(), ModuleConstant.STORAGE_MANAGE, dto.getStatus()));
        // TODO: 打印操作

        return ResultVO.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO insert(PrintLabelDTO dto) {
        if (StringUtils.isEmpty(dto.getScode())) {
            // 手动录入
            List<PrintLabelPO> plPOs = new ArrayList<>(dto.getPnum());
            int num = ReceiptNoUtil.getLabelNum(printLabelMapper, null, new Date());
            for (int i = 0; i < dto.getPnum(); i++) {
                plPOs.add(PrintLabelDTO.createPO(dto, ++num, ReceiptConstant.LABEL_ORIGIN_ENTRY));
            }
            printLabelMapper.batchInsert(plPOs);

            // 收料标签
            List<ReceiptLabelPO> rlPOs = new ArrayList<>(plPOs.size());
            for (PrintLabelPO plPO : plPOs) {
                ReceiptLabelPO rlPO = new ReceiptLabelPO();
                rlPO.setPrintLabelId(plPO.getPrintLabelId());
                rlPO.setReceiptBodyId(dto.getRbid());
                rlPOs.add(rlPO);
            }
            receiptLabelMapper.batchInsert(rlPOs);

            print(plPOs, ReceiptConstant.RECEIPT_ENTRY_LABEL);

            return ResultVO.ok();
        }

        // 扫描录入
        return ResultVO.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO delete(@RequestBody OperateDTO dto) {
        ReceiptBodyPO receiptBodyPO = receiptBodyMapper.selectByPrimaryKey(dto.getRbid());
        if (null == receiptBodyPO) {
            return new ResultVO(1002);
        }
        PrintLabelPO printLabelPO = printLabelMapper.selectByPrimaryKey(dto.getPlid());
        if (null == printLabelPO) {
            return new ResultVO(1002);
        }

        if (null != printLabelPO.getRelateLabelId()) {
            PrintLabelPO relateLabelPO = printLabelMapper.queryByRbidAndPid(dto.getRbid(), printLabelPO.getRelatePackageId());
            if (null == relateLabelPO) {
                return new ResultVO(1002);
            }
            printLabelMapper.deleteByRelateId(printLabelPO.getRelateLabelId());
            relateLabelPO.setDr((byte) 1);
            printLabelMapper.updateByPrimaryKey(relateLabelPO);
            return ResultVO.ok();
        }

        if (!ReceiptConstant.RECEIPT_ENTRY_LABEL.equals(receiptBodyPO.getStatus())) {
            return new ResultVO(1003);
        }

        printLabelMapper.deleteByPrimaryKey(dto.getPlid());

        return ResultVO.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO finish(@RequestBody OperateDTO dto) {
        UserPO user = tokenService.getLoginUser();
        ReceiptBodyBO bodyPO = receiptBodyMapper.queryByBodyId(dto.getRbid());
        if (null == bodyPO) {
            return new ResultVO(1002);
        }
        if (!ReceiptConstant.RECEIPT_ENTRY_LABEL.equals(bodyPO.getStatus())) {
            return new ResultVO(1003);
        }

        // 打印标签总数
        List<PrintLabelPO> labelPOs = printLabelMapper.queryByReceiptBodyId(dto.getRbid(), (byte) 1);
        int labelNum = 0;
        for (PrintLabelPO labelPO : labelPOs) {
            labelNum += labelPO.getNum();
        }
        if (labelNum < bodyPO.getAcceptNum()) {
            return new ResultVO(1005);
        }

        ConfigPO configPO = configMapper.queryByKey(ConfigConstant.K_STORAGE_QUALITY);
        MaterialAttributePO attributePO = materialAttributeMapper.queryByMid(bodyPO.getMaterialId());
        if (null != configPO && ConfigConstant.V_YES.equals(configPO.getConfigValue())) {
            if (null != attributePO && null != attributePO.getStorageInspect() && !ReceiptConstant.STORAGE_INSPECT_NO.equals(attributePO.getStorageInspect())) {
                Byte status;    // 操作记录类型
                if (ReceiptConstant.STORAGE_INSPECT_IQC.equals(attributePO.getStorageInspect())) {
                    // IQC
                    IqcDetectPO iqcPO = new IqcDetectPO();
                    iqcPO.setReceiptBodyId(dto.getRbid());
                    iqcPO.setStatus(ReceiptConstant.IQC_WAIT);
                    iqcDetectMapper.insert(iqcPO);

                    status = ReceiptConstant.RECEIPT_IQC_DETECT;
                    bodyPO.setStatus(ReceiptConstant.RECEIPT_IQC_DETECT);
                } else if (ReceiptConstant.STORAGE_INSPECT_QE.equals(attributePO.getStorageInspect())) {
                    // QE
                    QeDetectPO qePO = new QeDetectPO();
                    qePO.setReceiptBodyId(dto.getRbid());
                    qePO.setStatus(ReceiptConstant.QE_WAIT);
                    qeDetectMapper.insert(qePO);

                    status = ReceiptConstant.RECEIPT_QE_DETECT;
                    bodyPO.setStatus(ReceiptConstant.RECEIPT_QE_DETECT);
                } else {
                    return new ResultVO(1002);
                }

                // 更新收料单状态
                receiptBodyMapper.updateByPrimaryKey(bodyPO);

                recordMapper.insert(new StorageRecordPO(dto.getRbid(), user.getUserId(), user.getName(), ReceiptConstant.RECORD_TYPE_ADD, status));
                recordMapper.insert(new StorageRecordPO(dto.getRbid(), user.getUserId(), user.getName(), ReceiptConstant.RECORD_TYPE_FINISH, ReceiptConstant.RECEIPT_ENTRY_LABEL));

                return ResultVO.ok();
            }
        }

        // 不进行质量管理
        // 更新收料单状态
        bodyPO.setStatus(ReceiptConstant.RECEIPT_MATERIAL_STORAGE);
        bodyPO.setGoodNum(labelNum);
        bodyPO.setBadNum(0);
        bodyPO.setStockNum(0);
        receiptBodyMapper.updateByPrimaryKey(bodyPO);

        // 生产出库单
        StoragePO storagePO = new StoragePO();
        storagePO.setSourceNo(bodyPO.getReceiptNo());
        storagePO.setSourceType((byte) 1);
        storagePO.setStorageNo(ReceiptNoUtil.genStorageNo(storageMapper, ReceiptNoUtil.MATERIAL_STORAGE_LPPK, new Date()));
        storagePO.setPendingNum(bodyPO.getGoodNum());
        storagePO.setStoredNum(0);
        storagePO.setStatus(ReceiptConstant.MATERIAL_STORAGE_PENDING);
        storagePO.setType(ReceiptConstant.MATERIAL_TYPE_GOOD);
        storagePO.setCreateTime(new Date());
        storagePO.setDr((byte) 1);
        if (attributePO != null) {
            storagePO.setWarehouseId(attributePO.getWarehouseId());
        }
        storageMapper.insert(storagePO);
        // 操作记录
        recordMapper.insert(new StorageRecordPO(dto.getRbid(), storagePO.getStorageId(), user.getUserId(), user.getName(), ReceiptConstant.RECORD_TYPE_STORAGE_INVOICE, ReceiptConstant.RECEIPT_MATERIAL_STORAGE));
        recordMapper.insert(new StorageRecordPO(dto.getRbid(), user.getUserId(), user.getName(), ReceiptConstant.RECORD_TYPE_FINISH, ReceiptConstant.RECEIPT_ENTRY_LABEL));

        return ResultVO.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO reprint(OperateDTO dto) {
        PrintLabelPO labelPO = printLabelMapper.selectByPrimaryKey(dto.getPlid());
        if (null == labelPO) {
            return new ResultVO(1002);
        }
        ReceiptLabelPO rlPO = receiptLabelMapper.queryByPrintLabelId(labelPO.getPrintLabelId());
        if (null == rlPO) {
            return new ResultVO(1002);
        }
        ReceiptBodyPO rbPO = receiptBodyMapper.selectByPrimaryKey(rlPO.getReceiptBodyId());
        if (null == rbPO) {
            return new ResultVO(1002);
        }

        // 废弃原有标签
        labelPO.setDr((byte) 2);
        printLabelMapper.updateByPrimaryKey(labelPO);

        // 新的标签
        List<PrintLabelPO> newPlPOs = new ArrayList<>();

        int curNum = ReceiptNoUtil.getLabelNum(printLabelMapper, null, new Date());
        if (dto.getNum() > 0) {
            PrintLabelPO newPlPO = PrintLabelPO.buildPO(labelPO, ReceiptNoUtil.genLabelNo(null, new Date(), ++curNum), dto.getNum(), null);
            printLabelMapper.insert(newPlPO);

            rlPO.setReceiptLabelId(null);
            rlPO.setPrintLabelId(newPlPO.getPrintLabelId());
            receiptLabelMapper.insert(rlPO);

            newPlPOs.add(newPlPO);
        }

        print(newPlPOs, rbPO.getStatus());

        return ResultVO.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO split(LabelSplitDTO dto) {
        PrintLabelPO labelPO = printLabelMapper.selectByPrimaryKey(dto.getPlid());
        if (null == labelPO) {
            return new ResultVO(1002);
        }
        if (dto.getGnum() + dto.getBnum() != labelPO.getNum()) {
            return new ResultVO(1001);
        }

        ReceiptLabelPO rlPO = receiptLabelMapper.queryByPrintLabelId(labelPO.getPrintLabelId());
        if (null == rlPO) {
            return new ResultVO(1002);
        }
        ReceiptBodyPO rbPO = receiptBodyMapper.selectByPrimaryKey(rlPO.getReceiptBodyId());
        if (null == rbPO) {
            return new ResultVO(1002);
        }

        // 废弃原有标签
        labelPO.setDr((byte) 2);
        printLabelMapper.updateByPrimaryKey(labelPO);

        // 新的标签
        List<PrintLabelPO> newPlPOs = new ArrayList<>();

        // 生产新的打印标签
        int num = ReceiptNoUtil.getLabelNum(printLabelMapper, null, new Date());
        if (dto.getGnum() > 0) {
            PrintLabelPO newPlPO = PrintLabelPO.buildPO(labelPO, ReceiptNoUtil.genLabelNo(null, new Date(), ++num), dto.getGnum(), ReceiptConstant.LABEL_TYPE_GOOD);
            printLabelMapper.insert(newPlPO);

            rlPO.setReceiptLabelId(null);
            rlPO.setPrintLabelId(newPlPO.getPrintLabelId());
            receiptLabelMapper.insert(rlPO);

            newPlPOs.add(newPlPO);
        }
        if (dto.getBnum() > 0) {
            PrintLabelPO newPlPO = PrintLabelPO.buildPO(labelPO, ReceiptNoUtil.genLabelNo(null, new Date(), ++num), dto.getBnum(), ReceiptConstant.LABEL_TYPE_BAD);
            printLabelMapper.insert(newPlPO);

            rlPO.setReceiptLabelId(null);
            rlPO.setPrintLabelId(newPlPO.getPrintLabelId());
            receiptLabelMapper.insert(rlPO);

            newPlPOs.add(newPlPO);
        }

        print(newPlPOs, rbPO.getStatus());

        return ResultVO.ok();
    }


    /**
     * 打印操作
     *
     * @param plPOs
     * @param status
     */
    public void print(List<PrintLabelPO> plPOs, Byte status) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                new Thread(() -> {
                    for (PrintLabelPO plPO : plPOs) {
                        OperateDTO dto = new OperateDTO();
                        dto.setPid(plPO.getPackageId());
                        dto.setStatus(status);
                        print(dto);
                    }
                }).start();
            }
        });
    }
}
