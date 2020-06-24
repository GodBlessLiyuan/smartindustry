package com.smartindustry.storage.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.smartindustry.common.bo.ReceiptBO;
import com.smartindustry.common.mapper.EntryLabelMapper;
import com.smartindustry.common.mapper.ReceiptBodyMapper;
import com.smartindustry.common.mapper.ReceiptHeadMapper;
import com.smartindustry.common.mapper.RecordMapper;
import com.smartindustry.common.pojo.EntryLabelPO;
import com.smartindustry.common.pojo.ReceiptBodyPO;
import com.smartindustry.common.pojo.ReceiptHeadPO;
import com.smartindustry.common.pojo.RecordPO;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.dto.LogisticsDTO;
import com.smartindustry.storage.dto.ReceiptBodyDTO;
import com.smartindustry.storage.dto.ReceiptDTO;
import com.smartindustry.storage.dto.ReceiptHeadDTO;
import com.smartindustry.storage.service.IReceiptManageService;
import com.smartindustry.storage.vo.LogisticsVO;
import com.smartindustry.storage.vo.ReceiptPageVO;
import com.smartindustry.storage.vo.RecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @Override
    public ResultVO pageQuery(int pageNum, int pageSize, Map<String, Object> reqData) {
        Page<ReceiptBO> page = PageHelper.startPage(pageNum, pageSize);
        List<ReceiptBO> bos = receiptBodyMapper.pageQuery(reqData);
        return new ResultVO<>(1000, new PageInfoVO<>(page.getTotal(), ReceiptPageVO.convert(bos)));
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
    public ResultVO queryLog(Long rbId) {
        ReceiptBodyPO bodyPO = receiptBodyMapper.selectByPrimaryKey(rbId);
        ReceiptHeadPO headPO = receiptHeadMapper.selectByPrimaryKey(bodyPO.getReceiptHeadId());
        return new ResultVO<>(1000, LogisticsVO.convert(headPO));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO editLog(LogisticsDTO dto) {
        ReceiptBodyPO bodyPO = receiptBodyMapper.selectByPrimaryKey(dto.getRbId());
        ReceiptHeadPO headPO = receiptHeadMapper.selectByPrimaryKey(bodyPO.getReceiptHeadId());
        receiptHeadMapper.updateByPrimaryKey(LogisticsDTO.buildPO(headPO, dto));

        RecordPO recordPO = new RecordPO();
        recordPO.setReceiptBodyId(dto.getRbId());
        recordPO.setUserId((long) 1);
        recordPO.setName("夏慧");
        recordPO.setType((byte) 2);
        recordPO.setCreateTime(new Date());
        recordMapper.insert(recordPO);
        
        return new ResultVO(1000);
    }

    @Override
    public ResultVO record(Long rbId, byte order) {
        List<RecordPO> pos = recordMapper.queryByReceiptBodyId(rbId, order);
        return new ResultVO<>(1000, RecordVO.convert(pos));
    }
}
