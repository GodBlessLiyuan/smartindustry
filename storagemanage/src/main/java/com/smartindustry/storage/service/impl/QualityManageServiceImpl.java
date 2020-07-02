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
import com.smartindustry.storage.dto.QeConfirmDTO;
import com.smartindustry.storage.service.IQualityManageService;
import com.smartindustry.storage.util.ReceiptNoUtil;
import com.smartindustry.storage.vo.PrintLabelVO;
import com.smartindustry.storage.vo.QualityPageVO;
import com.smartindustry.storage.vo.ReceiptPageVO;
import com.smartindustry.storage.vo.RecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), QualityPageVO.convert(bos)));
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

            // QE确认
            QeConfirmPO qeConfirmPO = new QeConfirmPO();
            qeConfirmPO.setReceiptBodyId(dto.getRbid());
            qeConfirmPO.setStatus(ReceiptConstant.QE_CONFIRM_WAIT);
            qeConfirmMapper.insert(qeConfirmPO);

            // 操作记录
            recordMapper.insert(new RecordPO(null, dto.getRbid(), 1L, "夏慧", ReceiptConstant.RECORD_TYPE_ADD, new Date(), ReceiptConstant.RECEIPT_QE_CONFIRM));
        }

        ReceiptBodyPO receiptBodyPO = receiptBodyMapper.selectByPrimaryKey(dto.getRbid());
        receiptBodyPO.setGoodNum(dto.getGnum());
        receiptBodyPO.setBadNum(dto.getBnum());
        if (dto.getStatus() == 2) {
            receiptBodyPO.setStatus(ReceiptConstant.RECEIPT_QE_CONFIRM);
        }
        receiptBodyMapper.updateByPrimaryKey(receiptBodyPO);

        // 操作记录
        String type = ReceiptConstant.IQC_DETECT_WAIT.equals(iqcDetectPO.getStatus()) ? ReceiptConstant.RECORD_TYPE_IQC_DETECT : ReceiptConstant.RECORD_TYPE_IQC_RECHECK;
        recordMapper.insert(new RecordPO(null, dto.getRbid(), 1L, "夏慧", type, new Date(), ReceiptConstant.RECEIPT_IQC_DETECT));

        return ResultVO.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO qeConfirm(QeConfirmDTO dto) {
        QeConfirmPO qeConfirmPO = qeConfirmMapper.selectByPrimaryKey(dto.getRbid());
        if (null == qeConfirmPO || !ReceiptConstant.QE_CONFIRM_WAIT.equals(qeConfirmPO.getStatus())) {
            return new ResultVO(2000);
        }

        ReceiptBodyPO receiptBodyPO = receiptBodyMapper.selectByPrimaryKey(dto.getRbid());
        if (dto.getStatus() == 1) {
            // 特采
            qeConfirmPO.setStatus(ReceiptConstant.QE_CONFIRM_FRANCHISE);
            qeConfirmPO.setRemark(dto.getRemark());
            qeConfirmMapper.updateByPrimaryKey(qeConfirmPO);

            receiptBodyPO.setGoodNum(dto.getNum());
            receiptBodyPO.setBadNum(receiptBodyPO.getAcceptNum() - dto.getNum());
        } else if (dto.getStatus() == 2) {
            // 驳回
            qeConfirmMapper.deleteByPrimaryKey(dto.getRbid());

            // IQC 检验
            IqcDetectPO iqcDetectPO = new IqcDetectPO();
            iqcDetectPO.setReceiptBodyId(dto.getRbid());
            iqcDetectPO.setStatus(ReceiptConstant.IQC_DETECT_REJECT);
            iqcDetectPO.setRemark(dto.getRemark());
            iqcDetectMapper.insert(iqcDetectPO);

            receiptBodyPO.setStatus(ReceiptConstant.RECEIPT_IQC_DETECT);

            recordMapper.insert(new RecordPO(null, dto.getRbid(), 1L, "夏慧", ReceiptConstant.RECORD_TYPE_QE_REJECT, new Date(), ReceiptConstant.RECEIPT_IQC_DETECT));
        } else if (dto.getStatus() == 3) {
            // 退供应商
            qeConfirmPO.setRemark(dto.getRemark());
            qeConfirmPO.setStatus(ReceiptConstant.QE_CONFIRM_RETURN);
            qeConfirmMapper.updateByPrimaryKey(qeConfirmPO);

            receiptBodyPO.setGoodNum(0);
            receiptBodyPO.setBadNum(receiptBodyPO.getAcceptNum());
        }
        receiptBodyMapper.updateByPrimaryKey(receiptBodyPO);

        String status = dto.getStatus() == 1 ? ReceiptConstant.RECORD_TYPE_QE_FRANCHISE : dto.getStatus() == 2 ? ReceiptConstant.RECORD_TYPE_QE_REJECT : ReceiptConstant.RECORD_TYPE_QE_RETURN;
        recordMapper.insert(new RecordPO(null, dto.getRbid(), 1L, "夏慧", status, new Date(), ReceiptConstant.RECEIPT_QE_CONFIRM));

        return ResultVO.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO storage(Long rbId) {
        IqcDetectPO iqcDetectPO = iqcDetectMapper.selectByPrimaryKey(rbId);
        // 操作记录类型
        Byte status = ReceiptConstant.RECEIPT_IQC_DETECT;
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
                status = ReceiptConstant.RECEIPT_QE_CONFIRM;
            } else {
                // QE检验
                QeDetectPO qeDetectPO = qeDetectMapper.selectByPrimaryKey(rbId);
                if (null == qeDetectPO || ReceiptConstant.QE_DETECT_GOOD.equals(qeDetectPO.getStatus())) {
                    return new ResultVO(2000);
                }
                qeDetectMapper.deleteByPrimaryKey(rbId);
                status = ReceiptConstant.RECEIPT_QE_DETECT;
            }
        }

        ReceiptBodyPO receiptBodyPO = receiptBodyMapper.selectByPrimaryKey(rbId);
        receiptBodyPO.setStatus(ReceiptConstant.RECEIPT_MATERIAL_STORAGE);
        receiptBodyPO.setStockNum(0);
        receiptBodyMapper.updateByPrimaryKey(receiptBodyPO);

        if (receiptBodyPO.getGoodNum() > 0) {
            // 良品入库单
            MaterialStoragePO materialStoragePO = new MaterialStoragePO();
            materialStoragePO.setReceiptBodyId(rbId);
            materialStoragePO.setStorageNo(ReceiptNoUtil.genStorageNo(materialStorageMapper, ReceiptNoUtil.MATERIAL_STORAGE_PK, new Date()));
            materialStoragePO.setCreateTime(new Date());
            materialStoragePO.setStatus(ReceiptConstant.MATERIAL_STORAGE_PENDING);
            materialStoragePO.setPendingNum(receiptBodyPO.getGoodNum());
            materialStoragePO.setStoredNum(0);
            materialStoragePO.setType(ReceiptConstant.MATERIAL_TYPE_GOOD);
            materialStorageMapper.insert(materialStoragePO);
        }
        if (receiptBodyPO.getBadNum() > 0) {
            // 非良品入库单
            MaterialStoragePO materialStoragePO = new MaterialStoragePO();
            materialStoragePO.setReceiptBodyId(rbId);
            materialStoragePO.setStorageNo(ReceiptNoUtil.genStorageNo(materialStorageMapper, ReceiptNoUtil.MATERIAL_STORAGE_PK, new Date()));
            materialStoragePO.setCreateTime(new Date());
            materialStoragePO.setStatus(ReceiptConstant.MATERIAL_STORAGE_PENDING);
            materialStoragePO.setPendingNum(receiptBodyPO.getBadNum());
            materialStoragePO.setStoredNum(0);
            materialStoragePO.setType(ReceiptConstant.MATERIAL_TYPE_BAD);
            materialStorageMapper.insert(materialStoragePO);
        }

        // 操作记录
        recordMapper.insert(new RecordPO(null, rbId, 1L, "夏慧", ReceiptConstant.RECORD_TYPE_STORAGE_INVOICE, new Date(), status));
        recordMapper.insert(new RecordPO(null, rbId, 1L, "夏慧", ReceiptConstant.RECORD_TYPE_STORAGE_INVOICE, new Date(), ReceiptConstant.RECEIPT_MATERIAL_STORAGE));

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
