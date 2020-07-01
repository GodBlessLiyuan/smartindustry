package com.smartindustry.storage.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.smartindustry.common.bo.ReceiptBO;
import com.smartindustry.common.mapper.*;
import com.smartindustry.common.pojo.*;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.constant.ReceiptConstant;
import com.smartindustry.storage.dto.IqcTestDTO;
import com.smartindustry.storage.service.IQualityManageService;
import com.smartindustry.storage.util.ReceiptNoUtil;
import com.smartindustry.storage.vo.PrintLabelVO;
import com.smartindustry.storage.vo.ReceiptPageVO;
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
    private QeDetectMapper qeDetectMapper;
    @Autowired
    private QeConfirmMapper qeConfirmMapper;
    @Autowired
    private ReceiptBodyMapper receiptBodyMapper;
    @Autowired
    private MaterialStorageMapper materialStorageMapper;

    @Override
    public ResultVO pageQuery(int pageNum, int pageSize, Map<String, Object> reqData) {
        Page<ReceiptBO> page = PageHelper.startPage(pageNum, pageSize);
        List<ReceiptBO> bos = receiptBodyMapper.pageQuery(reqData);

        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), ReceiptPageVO.convert(bos)));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO iqcTest(IqcTestDTO dto) {
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
            receiptBodyMapper.updateByPrimaryKey(receiptBodyPO);

            // 操作记录
            recordMapper.insert(new RecordPO(null, dto.getRbid(), 1L, "夏慧", ReceiptConstant.RECORD_TYPE_ADD, new Date(), ReceiptConstant.RECEIPT_QE_CONFIRM));
        }

        // 操作记录
        String type = ReceiptConstant.IQC_DETECT_WAIT.equals(iqcDetectPO.getStatus()) ? ReceiptConstant.RECORD_TYPE_IQC_DETECT : ReceiptConstant.RECORD_TYPE_IQC_RECHECK;
        recordMapper.insert(new RecordPO(null, dto.getRbid(), 1L, "夏慧", type, new Date(), ReceiptConstant.RECEIPT_IQC_DETECT));

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO storage(Long rbId) {
        IqcDetectPO iqcDetectPO = iqcDetectMapper.selectByPrimaryKey(rbId);
        if (null != iqcDetectPO) {
            // IQC检验
            if (!ReceiptConstant.IQC_DETECT_GOOD.equals(iqcDetectPO.getStatus())) {
                return new ResultVO(2000);
            }
            iqcDetectMapper.deleteByPrimaryKey(rbId);
        } else {
            // QE确认
            QeConfirmPO qeConfirmPO = qeConfirmMapper.selectByPrimaryKey(rbId);
            if (null != qeConfirmPO) {
                if (!ReceiptConstant.QE_CONFIRM_FRANCHISE.equals(qeConfirmPO.getStatus())) {
                    return new ResultVO(2000);
                }
                qeConfirmMapper.deleteByPrimaryKey(rbId);
            } else {
                // QE检验
                QeDetectPO qeDetectPO = qeDetectMapper.selectByPrimaryKey(rbId);
                if (null == qeDetectPO || ReceiptConstant.QE_DETECT_GOOD.equals(qeDetectPO.getStatus())) {
                    return new ResultVO(2000);
                }
                qeDetectMapper.deleteByPrimaryKey(rbId);
            }
        }

        ReceiptBodyPO receiptBodyPO = receiptBodyMapper.selectByPrimaryKey(rbId);
        receiptBodyPO.setStatus(ReceiptConstant.RECEIPT_MATERIAL_STORAGE);
        receiptBodyMapper.updateByPrimaryKey(receiptBodyPO);

        // 生成入库单
        MaterialStoragePO materialStoragePO = new MaterialStoragePO();
        materialStoragePO.setReceiptBodyId(rbId);
        materialStoragePO.setStorageNo(ReceiptNoUtil.genStorageNo(materialStorageMapper, ReceiptNoUtil.MATERIAL_STORAGE_PK, new Date()));
        materialStoragePO.setCreateTime(new Date());
        materialStoragePO.setStatus(ReceiptConstant.MATERIAL_STORAGE_PENDING);
        materialStoragePO.setPendingNum(receiptBodyPO.getAcceptNum());
        materialStoragePO.setStoredNum(0);
        materialStorageMapper.insert(materialStoragePO);

        return ResultVO.ok();
    }
}
