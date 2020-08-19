package com.smartindustry.storage.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.smartindustry.common.bo.si.LabelRecordBO;
import com.smartindustry.common.bo.sm.ReceiptBO;
import com.smartindustry.common.constant.ModuleConstant;
import com.smartindustry.common.constant.ResultConstant;
import com.smartindustry.common.mapper.si.LabelRecordMapper;
import com.smartindustry.common.mapper.sm.*;
import com.smartindustry.common.pojo.am.UserPO;
import com.smartindustry.common.pojo.sm.*;
import com.smartindustry.common.security.service.TokenService;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.util.ServletUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.constant.ReceiptConstant;
import com.smartindustry.storage.dto.IqcTestDTO;
import com.smartindustry.storage.dto.OperateDTO;
import com.smartindustry.storage.dto.QeConfirmDTO;
import com.smartindustry.storage.dto.QeTestDTO;
import com.smartindustry.storage.service.IQualityManageService;
import com.smartindustry.storage.util.ReceiptNoUtil;
import com.smartindustry.storage.vo.LabelRecordVO;
import com.smartindustry.storage.vo.QualityPageVO;
import com.smartindustry.storage.vo.RecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

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
    private StorageRecordMapper recordMapper;
    @Autowired
    private IqcDetectMapper iqcDetectMapper;
    @Autowired
    private QeDetectMapper qeDetectMapper;
    @Autowired
    private QeConfirmMapper qeConfirmMapper;
    @Autowired
    private ReceiptBodyMapper receiptBodyMapper;
    @Autowired
    private StorageMapper storageMapper;
    @Autowired
    private LabelRecordMapper labelRecordMapper;
    @Autowired
    TokenService tokenService;

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData) {
        if (null == reqData.get("status")) {
            return new ResultVO(1001);
        }
        Byte status = Byte.valueOf(reqData.get("status").toString());
        if (!ReceiptConstant.RECEIPT_IQC_DETECT.equals(status) && !ReceiptConstant.RECEIPT_QE_DETECT.equals(status) && !ReceiptConstant.RECEIPT_QE_CONFIRM.equals(status)) {
            return new ResultVO(1001);
        }

        // 质量管理 分页查询查询
        reqData.put("qa", true);
        Page<ReceiptBO> page = PageQueryUtil.startPage(reqData);
        List<ReceiptBO> bos = receiptBodyMapper.pageQuery(reqData);

        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), QualityPageVO.convert(bos)));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO iqcTest(IqcTestDTO dto) {
        UserPO user = tokenService.getLoginUser().getUser();
        ReceiptBodyPO receiptBodyPO = receiptBodyMapper.selectByPrimaryKey(dto.getRbid());
        if (null == receiptBodyPO) {
            return new ResultVO(1002);
        }
        if (!ReceiptConstant.RECEIPT_IQC_DETECT.equals(receiptBodyPO.getStatus())) {
            return new ResultVO(1003);
        }

        // IQC检验
        IqcDetectPO iqcDetectPO = iqcDetectMapper.selectByPrimaryKey(dto.getRbid());
        if (null == iqcDetectPO) {
            return new ResultVO(1002);
        }
        if (ReceiptConstant.IQC_ALLOW.equals(iqcDetectPO.getStatus())) {
            return new ResultVO(1003);
        }

        String type;
        if (ReceiptConstant.IQC_ALLOW.equals(dto.getStatus())) {
            // 允许
            type = ReceiptConstant.IQC_REJECT.equals(iqcDetectPO.getStatus()) ? ReceiptConstant.RECORD_TYPE_IQC_RECHECK_ALLOW : ReceiptConstant.RECORD_TYPE_IQC_DETECT_ALLOW;

            iqcDetectPO.setStatus(ReceiptConstant.IQC_ALLOW);
            iqcDetectMapper.updateByPrimaryKey(iqcDetectPO);
        } else if (dto.getStatus() == 2) {
            // 不良
            type = ReceiptConstant.IQC_REJECT.equals(iqcDetectPO.getStatus()) ? ReceiptConstant.RECORD_TYPE_IQC_RECHECK_BAD : ReceiptConstant.RECORD_TYPE_IQC_DETECT_BAD;

            iqcDetectMapper.deleteByPrimaryKey(dto.getRbid());

            // QE确认
            QeConfirmPO qeConfirmPO = new QeConfirmPO();
            qeConfirmPO.setReceiptBodyId(dto.getRbid());
            qeConfirmPO.setStatus(ReceiptConstant.QE_WAIT);
            qeConfirmMapper.insert(qeConfirmPO);

            // 收料单状态设为QE确认
            receiptBodyPO.setStatus(ReceiptConstant.RECEIPT_QE_CONFIRM);

            if (!ReceiptConstant.IQC_REJECT.equals(iqcDetectPO.getStatus())) {
                // QE确认新增操作记录
                recordMapper.insert(new StorageRecordPO(dto.getRbid(), user.getUserId(), user.getName(), ReceiptConstant.RECORD_TYPE_QE_CONFIRM_ADD, ReceiptConstant.RECEIPT_QE_CONFIRM));
            }
        } else {
            return new ResultVO(1001);
        }

        // 更新收料单
        receiptBodyPO.setGoodNum(dto.getGnum());
        receiptBodyPO.setBadNum(dto.getBnum());
        receiptBodyMapper.updateByPrimaryKey(receiptBodyPO);

        // 操作记录
        recordMapper.insert(new StorageRecordPO(dto.getRbid(), user.getUserId(), user.getName(), type, ReceiptConstant.RECEIPT_IQC_DETECT));

        return ResultVO.ok();
    }

    @Override
    public ResultVO qeTest(QeTestDTO dto) {
        UserPO user = tokenService.getLoginUser().getUser();
        ReceiptBodyPO receiptBodyPO = receiptBodyMapper.selectByPrimaryKey(dto.getRbid());
        if (null == receiptBodyPO) {
            return new ResultVO(1002);
        }
        if (!ReceiptConstant.RECEIPT_QE_DETECT.equals(receiptBodyPO.getStatus())) {
            return new ResultVO(1003);
        }

        // QE 检测
        QeDetectPO qeDetectPO = qeDetectMapper.selectByPrimaryKey(dto.getRbid());
        if (null == qeDetectPO) {
            return new ResultVO(1002);
        }
        if (!ReceiptConstant.QE_WAIT.equals(qeDetectPO.getStatus())) {
            return new ResultVO(1003);
        }

        String type;
        if (ReceiptConstant.QE_ALLOW.equals(dto.getStatus())) {
            // 允许
            type = ReceiptConstant.RECORD_TYPE_QE_DETECT_ALLOW;
            qeDetectPO.setStatus(ReceiptConstant.QE_ALLOW);

            receiptBodyPO.setGoodNum(dto.getGnum());
            receiptBodyPO.setBadNum(dto.getBnum());
        } else if (ReceiptConstant.QE_FRANCHISE.equals(dto.getStatus())) {
            // 特采
            type = ReceiptConstant.RECORD_TYPE_QE_DETECT_FRANCHISE;
            qeDetectPO.setStatus(ReceiptConstant.QE_FRANCHISE);

            receiptBodyPO.setGoodNum(dto.getGnum());
            receiptBodyPO.setBadNum(receiptBodyPO.getAcceptNum() - dto.getGnum());
        } else if (ReceiptConstant.QE_RETURN.equals(dto.getStatus())) {
            // 退供应商
            type = ReceiptConstant.RECORD_TYPE_QE_DETECT_RETURN;
            qeDetectPO.setStatus(ReceiptConstant.QE_RETURN);

            receiptBodyPO.setGoodNum(0);
            receiptBodyPO.setBadNum(receiptBodyPO.getAcceptNum());
        } else {
            return new ResultVO(1001);
        }

        // 更新QE检测
        qeDetectPO.setRemark(dto.getRemark());
        qeDetectMapper.updateByPrimaryKey(qeDetectPO);
        // 更新收料单
        receiptBodyMapper.updateByPrimaryKey(receiptBodyPO);
        // 操作记录
        recordMapper.insert(new StorageRecordPO(dto.getRbid(), user.getUserId(), user.getName(), type, ReceiptConstant.RECEIPT_QE_DETECT));

        return ResultVO.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO qeConfirm(QeConfirmDTO dto) {
        UserPO user = tokenService.getLoginUser().getUser();
        ReceiptBodyPO receiptBodyPO = receiptBodyMapper.selectByPrimaryKey(dto.getRbid());
        if (null == receiptBodyPO) {
            return new ResultVO(1002);
        }
        if (!ReceiptConstant.RECEIPT_QE_CONFIRM.equals(receiptBodyPO.getStatus())) {
            return new ResultVO(1003);
        }

        QeConfirmPO qeConfirmPO = qeConfirmMapper.selectByPrimaryKey(dto.getRbid());
        if (null == qeConfirmPO) {
            return new ResultVO(1002);
        }
        if (!ReceiptConstant.QE_WAIT.equals(qeConfirmPO.getStatus())) {
            return new ResultVO(1003);
        }

        String type;    // 操作记录类型
        if (ReceiptConstant.QE_FRANCHISE.equals(dto.getStatus())) {
            // 特采
            type = ReceiptConstant.RECORD_TYPE_QE_CONFIRM_FRANCHISE;

            qeConfirmPO.setStatus(ReceiptConstant.QE_FRANCHISE);
            qeConfirmPO.setRemark(dto.getRemark());
            qeConfirmMapper.updateByPrimaryKey(qeConfirmPO);

            receiptBodyPO.setGoodNum(dto.getNum());
            receiptBodyPO.setBadNum(receiptBodyPO.getAcceptNum() - dto.getNum());
        } else if (ReceiptConstant.QE_REJECT.equals(dto.getStatus())) {
            // 驳回
            type = ReceiptConstant.RECORD_TYPE_QE_CONFIRM_REJECT;

            qeConfirmMapper.deleteByPrimaryKey(dto.getRbid());

            // IQC 检验
            IqcDetectPO iqcDetectPO = new IqcDetectPO();
            iqcDetectPO.setReceiptBodyId(dto.getRbid());
            iqcDetectPO.setStatus(ReceiptConstant.IQC_REJECT);
            iqcDetectPO.setRemark(dto.getRemark());
            iqcDetectMapper.insert(iqcDetectPO);

            // 收料单驳回到IQC
            receiptBodyPO.setStatus(ReceiptConstant.RECEIPT_IQC_DETECT);

            // IQC 操作记录
            recordMapper.insert(new StorageRecordPO(dto.getRbid(), user.getUserId(), user.getName(), ReceiptConstant.RECORD_TYPE_IQC_QE_REJECT, ReceiptConstant.RECEIPT_IQC_DETECT));
        } else if (ReceiptConstant.QE_RETURN.equals(dto.getStatus())) {
            // 退供应商
            type = ReceiptConstant.RECORD_TYPE_QE_CONFIRM_RETURN;

            qeConfirmPO.setRemark(dto.getRemark());
            qeConfirmPO.setStatus(ReceiptConstant.QE_RETURN);
            qeConfirmMapper.updateByPrimaryKey(qeConfirmPO);

            receiptBodyPO.setGoodNum(0);
            receiptBodyPO.setBadNum(receiptBodyPO.getAcceptNum());
        } else {
            return new ResultVO(1001);
        }

        // 更新收料单
        receiptBodyMapper.updateByPrimaryKey(receiptBodyPO);
        // QE 操作记录
        recordMapper.insert(new StorageRecordPO(dto.getRbid(), user.getUserId(), user.getName(), type, ReceiptConstant.RECEIPT_QE_CONFIRM));

        return ResultVO.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO storage(@RequestBody OperateDTO dto) {
        UserPO user = tokenService.getLoginUser().getUser();
        Byte status;    // 操作记录状态
        IqcDetectPO iqcDetectPO = iqcDetectMapper.selectByPrimaryKey(dto.getRbid());
        if (null != iqcDetectPO) {
            // IQC检验
            if (!ReceiptConstant.IQC_ALLOW.equals(iqcDetectPO.getStatus())) {
                return new ResultVO(1003);
            }
            iqcDetectMapper.deleteByPrimaryKey(dto.getRbid());

            status = ReceiptConstant.RECEIPT_IQC_DETECT;
        } else {
            // QE确认
            QeConfirmPO qeConfirmPO = qeConfirmMapper.selectByPrimaryKey(dto.getRbid());
            if (null != qeConfirmPO) {
                if (!ReceiptConstant.QE_FRANCHISE.equals(qeConfirmPO.getStatus()) && !ReceiptConstant.QE_RETURN.equals(qeConfirmPO.getStatus())) {
                    return new ResultVO(1003);
                }
                qeConfirmMapper.deleteByPrimaryKey(dto.getRbid());

                status = ReceiptConstant.RECEIPT_QE_CONFIRM;
            } else {
                // QE检验
                QeDetectPO qeDetectPO = qeDetectMapper.selectByPrimaryKey(dto.getRbid());
                if (null == qeDetectPO) {
                    return new ResultVO(1002);
                }
                qeDetectMapper.deleteByPrimaryKey(dto.getRbid());

                status = ReceiptConstant.RECEIPT_QE_DETECT;
            }
        }

        // 更新物料单
        ReceiptBodyPO receiptBodyPO = receiptBodyMapper.selectByPrimaryKey(dto.getRbid());
        receiptBodyPO.setStatus(ReceiptConstant.RECEIPT_MATERIAL_STORAGE);
        receiptBodyPO.setStockNum(0);
        receiptBodyMapper.updateByPrimaryKey(receiptBodyPO);

        if (receiptBodyPO.getGoodNum() > 0) {
            // 良品入库单
            StoragePO storagePO = new StoragePO();
            storagePO.setReceiptBodyId(dto.getRbid());
            storagePO.setStorageNo(ReceiptNoUtil.genStorageNo(storageMapper, ReceiptNoUtil.MATERIAL_STORAGE_LPPK, new Date()));
            storagePO.setPendingNum(receiptBodyPO.getGoodNum());
            storagePO.setStoredNum(0);
            storagePO.setStatus(ReceiptConstant.MATERIAL_STORAGE_PENDING);
            storagePO.setType(ReceiptConstant.MATERIAL_TYPE_GOOD);
            storagePO.setCreateTime(new Date());
            storagePO.setDr((byte) 1);
            storageMapper.insert(storagePO);

            recordMapper.insert(new StorageRecordPO(dto.getRbid(), storagePO.getStorageId(), user.getUserId(), user.getName(), ReceiptConstant.RECORD_TYPE_STORAGE_INVOICE, ReceiptConstant.RECEIPT_MATERIAL_STORAGE));
        }
        if (receiptBodyPO.getBadNum() > 0) {
            // 非良品入库单
            StoragePO storagePO = new StoragePO();
            storagePO.setReceiptBodyId(dto.getRbid());
            storagePO.setStorageNo(ReceiptNoUtil.genStorageNo(storageMapper, ReceiptNoUtil.MATERIAL_STORAGE_BLPK, new Date()));
            storagePO.setPendingNum(receiptBodyPO.getBadNum());
            storagePO.setStoredNum(0);
            storagePO.setStatus(ReceiptConstant.MATERIAL_STORAGE_PENDING);
            storagePO.setType(ReceiptConstant.MATERIAL_TYPE_BAD);
            storagePO.setCreateTime(new Date());
            storagePO.setDr((byte) 1);
            storageMapper.insert(storagePO);

            recordMapper.insert(new StorageRecordPO(dto.getRbid(), storagePO.getStorageId(), user.getUserId(), user.getName(), ReceiptConstant.RECORD_TYPE_STORAGE_INVOICE, ReceiptConstant.RECEIPT_MATERIAL_STORAGE));
        }

        // 操作记录
        recordMapper.insert(new StorageRecordPO(dto.getRbid(), user.getUserId(), user.getName(), ReceiptConstant.RECORD_TYPE_STORAGE_INVOICE, status));

        return ResultVO.ok();
    }

    @Override
    public ResultVO record(@RequestBody OperateDTO dto) {
        Map<String, Object> res = new HashMap<>();
        // 打印标签
        List<LabelRecordBO> labelRecordBOs = labelRecordMapper.queryByReceiptBodyId(dto.getRbid(), dto.getStatus(), ModuleConstant.STORAGE_MANAGE);
        res.put(ResultConstant.PRINT_DETAIL, LabelRecordVO.convert(labelRecordBOs));
        // 操作记录
        List<StorageRecordPO> recordPOs = recordMapper.queryByReceiptBodyId(dto.getRbid(), dto.getStatus());
        res.put(ResultConstant.OPERATE_RECORD, RecordVO.convert(recordPOs));

        return ResultVO.ok().setData(res);
    }
}
