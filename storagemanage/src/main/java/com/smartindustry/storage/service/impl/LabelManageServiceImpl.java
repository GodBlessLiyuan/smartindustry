package com.smartindustry.storage.service.impl;

import com.smartindustry.common.bo.sm.ReceiptBodyBO;
import com.smartindustry.common.mapper.si.PrintLabelMapper;
import com.smartindustry.common.mapper.sm.*;
import com.smartindustry.common.pojo.si.PrintLabelPO;
import com.smartindustry.common.pojo.sm.*;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.constant.ReceiptConstant;
import com.smartindustry.storage.dto.LabelSplitDTO;
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
    private ReceiptLabelMapper receiptLabelMapper;
    @Autowired
    private ReceiptBodyMapper receiptBodyMapper;
    @Autowired
    private IqcDetectMapper iqcDetectMapper;
    @Autowired
    private QeDetectMapper qeDetectMapper;
    @Autowired
    private StorageRecordMapper recordMapper;

    @Override
    public ResultVO query(Long rbId) {
        List<PrintLabelPO> pos = printLabelMapper.queryByReceiptBodyId(rbId);
        return ResultVO.ok().setData(PrintLabelVO.convert(pos));
    }

    @Override
    public ResultVO queryPid(Long rbId, String pid) {
        PrintLabelPO po = printLabelMapper.queryByRbidAndPid(rbId, pid);
        if (null == po) {
            return new ResultVO(1002);
        }
        return ResultVO.ok().setData(PrintLabelVO.convert(po));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO reprint(Long plId, Integer num) {
        PrintLabelPO labelPO = printLabelMapper.selectByPrimaryKey(plId);
        if (null == labelPO) {
            return new ResultVO(1002);
        }
        ReceiptLabelPO rlPO = receiptLabelMapper.queryByPrintLabelId(labelPO.getRelateLabelId());
        if (null == rlPO) {
            return new ResultVO(1002);
        }

        // 废弃原有标签
        labelPO.setDr((byte) 2);
        printLabelMapper.updateByPrimaryKey(labelPO);

        String existPid = labelPO.getPackageId();

        int curNum = ReceiptNoUtil.getLabelNum(printLabelMapper, null, new Date());
        labelPO.setPrintLabelId(null);
        labelPO.setPackageId(ReceiptNoUtil.genLabelNo(null, new Date(), ++curNum));
        labelPO.setNum(num);
        labelPO.setRelateLabelId(plId);
        labelPO.setRelatePackageId(existPid);
        labelPO.setDr((byte) 1);
        labelPO.setCreateTime(new Date());
        printLabelMapper.insert(labelPO);

        rlPO.setReceiptLabelId(null);
        rlPO.setPrintLabelId(labelPO.getPrintLabelId());
        receiptLabelMapper.insert(rlPO);

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

            return ResultVO.ok();
        }

        // 扫描录入
        return ResultVO.ok();
    }

    @Override
    public ResultVO delete(Long rbId, Long plId) {
        ReceiptBodyPO receiptBodyPO = receiptBodyMapper.selectByPrimaryKey(rbId);
        if (null == receiptBodyPO) {
            return new ResultVO(1002);
        }
        if (!ReceiptConstant.RECEIPT_ENTRY_LABEL.equals(receiptBodyPO.getStatus())) {
            return new ResultVO(1003);
        }

        printLabelMapper.deleteByPrimaryKey(plId);

        return ResultVO.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO finish(Long rbId) {
        ReceiptBodyBO bodyPO = receiptBodyMapper.queryByBodyId(rbId);
        if (null == bodyPO) {
            return new ResultVO(1002);
        }
        if (!ReceiptConstant.RECEIPT_ENTRY_LABEL.equals(bodyPO.getStatus())) {
            return new ResultVO(1003);
        }

        // 打印标签总数
        List<PrintLabelPO> labelPOs = printLabelMapper.queryByReceiptBodyId(rbId);
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
            iqcPO.setReceiptBodyId(rbId);
            iqcPO.setStatus(ReceiptConstant.IQC_WAIT);
            iqcDetectMapper.insert(iqcPO);

            status = ReceiptConstant.RECEIPT_IQC_DETECT;
            bodyPO.setStatus(ReceiptConstant.RECEIPT_IQC_DETECT);
        } else if (ReceiptConstant.MATERIAL_TYPE_SEMI.equals(bodyPO.getMaterialType())) {
            // 半成品/成品
            QeDetectPO qePO = new QeDetectPO();
            qePO.setReceiptBodyId(rbId);
            qePO.setStatus(ReceiptConstant.QE_WAIT);
            qeDetectMapper.insert(qePO);

            status = ReceiptConstant.RECEIPT_QE_DETECT;
            bodyPO.setStatus(ReceiptConstant.RECEIPT_QE_DETECT);
        } else {
            return new ResultVO(1002);
        }

        // 更新收料单状态
        receiptBodyMapper.updateByPrimaryKey(bodyPO);

        recordMapper.insert(new StorageRecordPO(rbId, 1L, "夏慧", ReceiptConstant.RECORD_TYPE_ADD, status));
        recordMapper.insert(new StorageRecordPO(rbId, 1L, "夏慧", ReceiptConstant.RECORD_TYPE_FINISH, ReceiptConstant.RECEIPT_ENTRY_LABEL));

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

        // 废弃原有标签
        labelPO.setDr((byte) 2);
        printLabelMapper.updateByPrimaryKey(labelPO);

        // 生产新的打印标签
        String existPid = labelPO.getPackageId();
        int num = ReceiptNoUtil.getLabelNum(printLabelMapper, null, new Date());
        if (dto.getGnum() > 0) {
            labelPO.setPrintLabelId(null);
            labelPO.setPackageId(ReceiptNoUtil.genLabelNo(null, new Date(), ++num));
            labelPO.setNum(dto.getGnum());
            labelPO.setType(ReceiptConstant.LABEL_TYPE_GOOD);
            labelPO.setRelateLabelId(dto.getPlid());
            labelPO.setRelatePackageId(existPid);
            labelPO.setDr((byte) 1);
            labelPO.setCreateTime(new Date());
            printLabelMapper.insert(labelPO);

            rlPO.setReceiptLabelId(null);
            rlPO.setPrintLabelId(labelPO.getPrintLabelId());
            receiptLabelMapper.insert(rlPO);
        }
        if (dto.getBnum() > 0) {
            labelPO.setPrintLabelId(null);
            labelPO.setPackageId(ReceiptNoUtil.genLabelNo(null, new Date(), ++num));
            labelPO.setNum(dto.getBnum());
            labelPO.setType(ReceiptConstant.LABEL_TYPE_BAD);
            printLabelMapper.insert(labelPO);

            rlPO.setReceiptLabelId(null);
            rlPO.setPrintLabelId(labelPO.getPrintLabelId());
            receiptLabelMapper.insert(rlPO);
        }

        return ResultVO.ok();
    }
}
