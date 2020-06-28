package com.smartindustry.storage.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.smartindustry.common.bo.ReceiptBO;
import com.smartindustry.common.mapper.*;
import com.smartindustry.common.pojo.*;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.dto.LogisticsDTO;
import com.smartindustry.storage.dto.ReceiptBodyDTO;
import com.smartindustry.storage.dto.ReceiptDTO;
import com.smartindustry.storage.dto.ReceiptHeadDTO;
import com.smartindustry.storage.service.IReceiptManageService;
import com.smartindustry.storage.vo.LogisticsVO;
import com.smartindustry.storage.vo.PrintLabelVO;
import com.smartindustry.storage.vo.ReceiptPageVO;
import com.smartindustry.storage.vo.RecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

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
    private EntryLabelMapper entryLabelMapper;
    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private PrintLabelMapper printLabelMapper;

    @Override
    public ResultVO pageQuery(int pageNum, int pageSize, Map<String, Object> reqData) {
        Page<ReceiptBO> page = PageHelper.startPage(pageNum, pageSize);
        List<ReceiptBO> bos = receiptBodyMapper.pageQuery(reqData);

        return ResultVO.ok().setPageInfoVO(new PageInfoVO<>(page.getTotal(), ReceiptPageVO.convert(bos)));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO insert(ReceiptDTO dto) {
        ReceiptHeadPO headPO = ReceiptHeadDTO.createPO(receiptHeadMapper, new ReceiptHeadPO(), dto.getHead());
        receiptHeadMapper.insert(headPO);

        List<ReceiptBodyPO> bodyPOs = ReceiptBodyDTO.createPOs(headPO, dto.getBody(), receiptBodyMapper);
        receiptBodyMapper.batchInsert(bodyPOs);

        List<EntryLabelPO> labelPOs = new ArrayList<>();
        for (ReceiptBodyPO bodyPO : bodyPOs) {
            EntryLabelPO labelPO = new EntryLabelPO();
            labelPO.setReceiptBodyId(bodyPO.getReceiptBodyId());
            labelPOs.add(labelPO);
        }
        entryLabelMapper.batchInsert(labelPOs);

        List<RecordPO> recordPOs = new ArrayList<>();
        for (ReceiptBodyPO bodyPO : bodyPOs) {
            RecordPO recordPO = new RecordPO();
            recordPO.setReceiptBodyId(bodyPO.getReceiptBodyId());
            recordPO.setUserId((long) 1);
            recordPO.setName("夏慧");
            recordPO.setType((byte) 1);
            recordPO.setCreateTime(new Date());
            recordPOs.add(recordPO);
        }
        recordMapper.batchInsert(recordPOs);

        return new ResultVO(1000);
    }

    @Override
    public ResultVO delete(List<Long> rbIds) {
        receiptBodyMapper.batchDelete(rbIds);

        // 删除表头信息
        new Thread(() -> {
            List<Long> headIds = receiptBodyMapper.queryHeadIds(rbIds);
            for (Long headId : headIds) {
                List<ReceiptBodyPO> bodyPOs = receiptBodyMapper.queryByHeadId(headId);
                if (null == bodyPOs || bodyPOs.size() == 0) {
                    receiptHeadMapper.deleteByPrimaryKey(headId);
                }
            }
        }).start();

        return new ResultVO(1000);
    }

    @Override
    public ResultVO record(Long rbId) {
        Map<String, Object> res = new HashMap<>();
        // 操作记录
        List<RecordPO> recordPOs = recordMapper.queryByReceiptBodyId(rbId);
        res.put("record", RecordVO.convert(recordPOs));

        // 物流信息
        ReceiptBodyPO bodyPO = receiptBodyMapper.selectByPrimaryKey(rbId);
        ReceiptHeadPO headPO = receiptHeadMapper.selectByPrimaryKey(bodyPO.getReceiptHeadId());
        res.put("logistics", LogisticsVO.convert(headPO));

        // 打印标签
        List<PrintLabelPO> printLabelPOs = printLabelMapper.queryByReceiptBodyId(rbId);
        res.put("print", PrintLabelVO.convert(printLabelPOs));

        return new ResultVO<>(1000, res);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO editLog(LogisticsDTO dto) {
        ReceiptBodyPO bodyPO = receiptBodyMapper.selectByPrimaryKey(dto.getRbid());
        ReceiptHeadPO headPO = receiptHeadMapper.selectByPrimaryKey(bodyPO.getReceiptHeadId());
        receiptHeadMapper.updateByPrimaryKey(LogisticsDTO.buildPO(headPO, dto));

        RecordPO recordPO = new RecordPO();
        recordPO.setReceiptBodyId(dto.getRbid());
        recordPO.setUserId((long) 1);
        recordPO.setName("夏慧");
        recordPO.setType((byte) 2);
        recordPO.setCreateTime(new Date());
        recordMapper.insert(recordPO);

        return new ResultVO(1000);
    }
}
