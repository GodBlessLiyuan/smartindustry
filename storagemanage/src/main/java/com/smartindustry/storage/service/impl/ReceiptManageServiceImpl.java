package com.smartindustry.storage.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.smartindustry.common.bo.si.LabelRecordBO;
import com.smartindustry.common.bo.sm.ReceiptBO;
import com.smartindustry.common.bo.sm.ReceiptBodyBO;
import com.smartindustry.common.constant.ModuleConstant;
import com.smartindustry.common.mapper.si.LabelRecordMapper;
import com.smartindustry.common.mapper.si.MaterialMapper;
import com.smartindustry.common.mapper.sm.*;
import com.smartindustry.common.pojo.si.MaterialPO;
import com.smartindustry.common.pojo.sm.ReceiptBodyPO;
import com.smartindustry.common.pojo.sm.ReceiptHeadPO;
import com.smartindustry.common.pojo.sm.StorageRecordPO;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.constant.ReceiptConstant;
import com.smartindustry.storage.dto.LogisticsDTO;
import com.smartindustry.storage.dto.ReceiptBodyDTO;
import com.smartindustry.storage.dto.ReceiptDTO;
import com.smartindustry.storage.dto.ReceiptHeadDTO;
import com.smartindustry.storage.service.IReceiptManageService;
import com.smartindustry.storage.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

/**
 * @author: xiahui
 * @date: Created in 2020/6/23 11:38
 * @description: 收料管理
 * @version: 1.0
 */
@EnableTransactionManagement
@Service
public class ReceiptManageServiceImpl implements IReceiptManageService {
    @Autowired
    private ReceiptHeadMapper receiptHeadMapper;
    @Autowired
    private ReceiptBodyMapper receiptBodyMapper;
    @Autowired
    private StorageRecordMapper recordMapper;
    @Autowired
    private LabelRecordMapper labelRecordMapper;
    @Autowired
    private MaterialMapper materialMapper;

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData) {
        Page<ReceiptBO> page = PageQueryUtil.startPage(reqData);
        List<ReceiptBO> bos = receiptBodyMapper.pageQuery(reqData);

        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), ReceiptPageVO.convert(bos)));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO insert(ReceiptDTO dto) {
        ReceiptHeadPO headPO = ReceiptHeadDTO.createPO(receiptHeadMapper, new ReceiptHeadPO(), dto.getHead());
        receiptHeadMapper.insert(headPO);

        List<ReceiptBodyBO> bodyBOs = ReceiptBodyDTO.createPOs(headPO, dto.getBody(), receiptBodyMapper);
        if (bodyBOs.size() == 0) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResultVO(1001);
        }

        receiptBodyMapper.batchInsertBO(bodyBOs);

        // 操作记录
        List<StorageRecordPO> recordPOs = new ArrayList<>(bodyBOs.size());
        for (ReceiptBodyBO bodyBO : bodyBOs) {
            recordPOs.add(new StorageRecordPO(bodyBO.getReceiptBodyId(), 1L, "夏慧", ReceiptConstant.RECORD_TYPE_ADD, ReceiptConstant.RECEIPT_ENTRY_LABEL));
        }
        recordMapper.batchInsert(recordPOs);

        return ResultVO.ok().setData(ReceiptVO.convert(headPO, bodyBOs));
    }

    @Override
    public ResultVO delete(List<Long> rbIds) {
        receiptBodyMapper.batchDelete(rbIds);

        // 删除表头信息
        new Thread(() -> {
            List<Long> headIds = receiptBodyMapper.queryHeadIds(rbIds);
            for (Long headId : headIds) {
                List<ReceiptBodyBO> bodyPOs = receiptBodyMapper.queryByHeadId(headId);
                if (null == bodyPOs || bodyPOs.size() == 0) {
                    receiptHeadMapper.deleteByPrimaryKey(headId);
                }
            }
        }).start();

        return ResultVO.ok();
    }

    @Override
    public ResultVO record(Long rbId, Byte status) {
        Map<String, Object> res = new HashMap<>();
        // 操作记录
        List<StorageRecordPO> recordPOs = recordMapper.queryByReceiptBodyId(rbId, status);
        res.put("record", RecordVO.convert(recordPOs));

        // 物流信息
        ReceiptBodyPO bodyPO = receiptBodyMapper.selectByPrimaryKey(rbId);
        ReceiptHeadPO headPO = receiptHeadMapper.selectByPrimaryKey(bodyPO.getReceiptHeadId());
        res.put("logistics", LogisticsVO.convert(headPO));

        // 打印标签
        List<LabelRecordBO> labelRecordBOs = labelRecordMapper.queryByReceiptBodyId(rbId, status, ModuleConstant.STORAGE_MANAGE);
        res.put("print", LabelRecordVO.convert(labelRecordBOs));

        return ResultVO.ok().setData(res);
    }

    @Override
    public ResultVO materialQuery(Map<String, Object> reqData) {
        Page<MaterialPO> page = PageQueryUtil.startPage(reqData);
        List<MaterialPO> pos = materialMapper.pageQuery(reqData);

        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), MaterialPageVO.convert(pos)));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO editLog(LogisticsDTO dto) {
        ReceiptBodyPO bodyPO = receiptBodyMapper.selectByPrimaryKey(dto.getRbid());
        ReceiptHeadPO headPO = receiptHeadMapper.selectByPrimaryKey(bodyPO.getReceiptHeadId());
        receiptHeadMapper.updateByPrimaryKey(LogisticsDTO.buildPO(headPO, dto));

        return ResultVO.ok();
    }
}
