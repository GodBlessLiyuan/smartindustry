package com.smartindustry.storage.service.impl;

import com.smartindustry.common.mapper.*;
import com.smartindustry.common.pojo.*;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.constant.ReceiptConstant;
import com.smartindustry.storage.dto.PrintLabelDTO;
import com.smartindustry.storage.service.ILabelManageService;
import com.smartindustry.storage.util.ReceiptNoUtil;
import com.smartindustry.storage.vo.PrintLabelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
    private ReceiptBodyMapper receiptBodyMapper;
    @Autowired
    private EntryLabelMapper entryLabelMapper;
    @Autowired
    private IqcDetectMapper iqcDetectMapper;
    @Autowired
    private QeDetectMapper qeDetectMapper;
    @Autowired
    private RecordMapper recordMapper;

    @Override
    public ResultVO query(Long rbId) {
        List<PrintLabelPO> pos = printLabelMapper.queryByReceiptBodyId(rbId);
        return ResultVO.ok().setData(PrintLabelVO.convert(pos));
    }

    @Override
    public ResultVO insert(PrintLabelDTO dto) {
        if (StringUtils.isEmpty(dto.getScode())) {
            // 手动录入
            List<PrintLabelPO> pos = new ArrayList<>();
            int num = ReceiptNoUtil.getLabelNum(printLabelMapper, null, new Date());
            Date curDate = new Date();
            for (int i = 0; i < dto.getPnum(); i++) {
                pos.add(PrintLabelDTO.createPO(dto, ++num, curDate));
            }
            printLabelMapper.batchInsert(pos);

            return ResultVO.ok();
        }

        // 扫描录入
        return ResultVO.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO finish(Long rbId) {
        ReceiptBodyPO bodyPO = receiptBodyMapper.selectByPrimaryKey(rbId);
        if (null == bodyPO) {
            return new ResultVO(2000);
        }
        if (!ReceiptConstant.RECEIPT_ENTRY_LABEL.equals(bodyPO.getStatus())) {
            return new ResultVO(2000);
        }

        List<PrintLabelPO> labelPOs = printLabelMapper.queryByReceiptBodyId(rbId);
        int labelNum = 0;
        for (PrintLabelPO labelPO : labelPOs) {
            labelNum += labelPO.getNum();
        }
        if (labelNum >= bodyPO.getAcceptNum()) {
            entryLabelMapper.deleteByPrimaryKey(rbId);
            if (bodyPO.getMaterialType() == 1) {
                // 原材料
                IqcDetectPO iqcPO = new IqcDetectPO();
                iqcPO.setReceiptBodyId(rbId);
                iqcPO.setStatus(ReceiptConstant.IQC_DETECT_WAIT);
                iqcDetectMapper.insert(iqcPO);
            } else {
                // 半成品/成品
                QeDetectPO qePO = new QeDetectPO();
                qePO.setReceiptBodyId(rbId);
                qePO.setStatus(ReceiptConstant.QE_CONFIRM_WAIT);
                qeDetectMapper.insert(qePO);
            }

            Byte status = bodyPO.getMaterialType() == 1 ? ReceiptConstant.RECEIPT_IQC_DETECT : ReceiptConstant.RECEIPT_QE_DETECT;
            recordMapper.insert(new RecordPO(null, rbId, 1L, "夏慧", ReceiptConstant.RECORD_TYPE_ADD, new Date(), status));

        }

        recordMapper.insert(new RecordPO(null, rbId, 1L, "夏慧", ReceiptConstant.RECORD_TYPE_FINISH, new Date(), ReceiptConstant.RECEIPT_ENTRY_LABEL));

        return ResultVO.ok();
    }
}
