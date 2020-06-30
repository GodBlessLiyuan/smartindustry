package com.smartindustry.storage.service.impl;

import com.smartindustry.common.mapper.*;
import com.smartindustry.common.pojo.*;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.constant.ReceiptConstant;
import com.smartindustry.storage.dto.IqcTestDTO;
import com.smartindustry.storage.service.IQualityManageService;
import com.smartindustry.storage.vo.PrintLabelVO;
import com.smartindustry.storage.vo.RecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.transform.Result;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/6/23 11:39
 * @description: 质量管理
 * @version: 1.0
 */
@EnableTransactionManagement
@Service
public class QualityManageServiceImpl implements IQualityManageService {
    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private PrintLabelMapper printLabelMapper;
    @Autowired
    private IqcDetectMapper iqcDetectMapper;
    @Autowired
    private QeConfirmMapper qeConfirmMapper;
    @Autowired
    private ReceiptBodyMapper receiptBodyMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO test(IqcTestDTO dto) {
        IqcDetectPO iqcDetectPO = iqcDetectMapper.selectByPrimaryKey(dto.getRbid());
        if (null == iqcDetectPO) {
            return new ResultVO(2000);
        }
        if (ReceiptConstant.IQC_DETECT_GOOD.equals(iqcDetectPO.getStatus())) {
            return new ResultVO(2000);
        }

        if (dto.getStatus() == 1) {
            // 允许
            iqcDetectPO.setStatus(ReceiptConstant.IQC_DETECT_GOOD);
            iqcDetectMapper.updateByPrimaryKey(iqcDetectPO);
        } else if (dto.getStatus() == 2) {
            // 不良
            iqcDetectMapper.deleteByPrimaryKey(dto.getRbid());

            QeConfirmPO qeConfirmPO = new QeConfirmPO();
            qeConfirmPO.setReceiptBodyId(dto.getRbid());
            qeConfirmPO.setStatus(ReceiptConstant.QE_CONFIRM_WAIT);
            qeConfirmMapper.insert(qeConfirmPO);

            ReceiptBodyPO receiptBodyPO = receiptBodyMapper.selectByPrimaryKey(dto.getRbid());
            receiptBodyPO.setGoodNum(dto.getGnum());
            receiptBodyPO.setBadNum(dto.getBnum());
            receiptBodyPO.setStatus(ReceiptConstant.RECEIPT_QE_CONFIRM);

            // 操作记录
            recordMapper.insert(new RecordPO(null, dto.getRbid(), 1L, "夏慧", ReceiptConstant.RECORD_TYPE_ADD, new Date(), ReceiptConstant.RECEIPT_QE_CONFIRM));
        }

        // 操作记录
        recordMapper.insert(new RecordPO(null, dto.getRbid(), 1L, "夏慧",
                iqcDetectPO.getStatus() == 3 ? ReceiptConstant.RECORD_TYPE_IQC_DETECT : ReceiptConstant.RECORD_TYPE_IQC_RECHECK,
                new Date(), ReceiptConstant.RECEIPT_IQC_DETECT));

        return ResultVO.ok();
    }

    @Override
    public ResultVO record(Long rbId, Byte status) {
        Map<String, Object> res = new HashMap<>();
        // 打印标签
        List<PrintLabelPO> printLabelPOs = printLabelMapper.queryByReceiptBodyId(rbId);
        res.put("print", PrintLabelVO.convert(printLabelPOs));
        // 操作记录
        List<RecordPO> recordPOs = recordMapper.queryByReceiptBodyId(rbId, status);
        res.put("record", RecordVO.convert(recordPOs));

        return ResultVO.ok().setData(res);
    }
}
