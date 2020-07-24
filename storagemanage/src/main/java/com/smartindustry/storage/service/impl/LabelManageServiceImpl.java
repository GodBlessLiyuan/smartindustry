package com.smartindustry.storage.service.impl;

import com.smartindustry.common.bo.sm.ReceiptBodyBO;
import com.smartindustry.common.constant.ModuleConstant;
import com.smartindustry.common.mapper.si.LabelRecordMapper;
import com.smartindustry.common.mapper.si.PrintLabelMapper;
import com.smartindustry.common.mapper.sm.*;
import com.smartindustry.common.pojo.si.LabelRecordPO;
import com.smartindustry.common.pojo.si.PrintLabelPO;
import com.smartindustry.common.pojo.sm.*;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.constant.ReceiptConstant;
import com.smartindustry.storage.dto.LabelSplitDTO;
import com.smartindustry.storage.dto.OperateDTO;
import com.smartindustry.storage.dto.PrintLabelDTO;
import com.smartindustry.storage.service.ILabelManageService;
import com.smartindustry.storage.util.ReceiptNoUtil;
import com.smartindustry.storage.vo.PrintLabelVO;
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

    @Override
    public ResultVO query(@RequestBody OperateDTO dto) {
        List<PrintLabelPO> pos = printLabelMapper.queryByReceiptBodyId(dto.getRbid(), dto.getStatus());
        return ResultVO.ok().setData(PrintLabelVO.convert(pos));
    }

    @Override
    public ResultVO queryPid(@RequestBody OperateDTO dto) {
        PrintLabelPO po = printLabelMapper.queryByRbidAndPid(dto.getRbid(), dto.getPid());
        if (null == po) {
            return new ResultVO(1002);
        }
        if (po.getDr() == 2) {
            return new ResultVO(1006);
        }
        return ResultVO.ok().setData(PrintLabelVO.convert(po));
    }

    @Override
    public ResultVO print(@RequestBody OperateDTO dto) {
        PrintLabelPO labelPO = printLabelMapper.queryByPidAndDr(dto.getPid(), (byte) 1);
        if (null == labelPO) {
            return new ResultVO(1002);
        }

        // 打印记录
        labelRecordMapper.insert(new LabelRecordPO(labelPO.getPrintLabelId(), 1L, "夏慧", ModuleConstant.STORAGE_MANAGE, dto.getStatus()));
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

        Byte status;    // 操作记录类型
        if (ReceiptConstant.MATERIAL_TYPE_RAW.equals(bodyPO.getMaterialType())) {
            // 原材料
            IqcDetectPO iqcPO = new IqcDetectPO();
            iqcPO.setReceiptBodyId(dto.getRbid());
            iqcPO.setStatus(ReceiptConstant.IQC_WAIT);
            iqcDetectMapper.insert(iqcPO);

            status = ReceiptConstant.RECEIPT_IQC_DETECT;
            bodyPO.setStatus(ReceiptConstant.RECEIPT_IQC_DETECT);
        } else if (ReceiptConstant.MATERIAL_TYPE_SEMI.equals(bodyPO.getMaterialType())) {
            // 半成品/成品
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

        recordMapper.insert(new StorageRecordPO(dto.getRbid(), 1L, "夏慧", ReceiptConstant.RECORD_TYPE_ADD, status));
        recordMapper.insert(new StorageRecordPO(dto.getRbid(), 1L, "夏慧", ReceiptConstant.RECORD_TYPE_FINISH, ReceiptConstant.RECEIPT_ENTRY_LABEL));

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
