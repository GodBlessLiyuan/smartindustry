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
import com.smartindustry.storage.dto.ReceiptBodyDTO;
import com.smartindustry.storage.dto.ReceiptDTO;
import com.smartindustry.storage.dto.ReceiptHeadDTO;
import com.smartindustry.storage.service.IReceiptManageService;
import com.smartindustry.storage.vo.ReceiptPageVO;
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
        ReceiptHeadPO headPO = ReceiptHeadDTO.buildPO(new ReceiptHeadPO(), dto.getHead());
        receiptHeadMapper.insert(headPO);

        List<ReceiptBodyPO> bodyPOs = ReceiptBodyDTO.createPOs(headPO.getReceiptHeadId(), dto.getBody(), receiptBodyMapper);
        receiptBodyMapper.batchInsert(bodyPOs);

        List<EntryLabelPO> labelPOs = new ArrayList<>();
        for (ReceiptBodyPO bodyPO : bodyPOs) {
            EntryLabelPO labelPO = new EntryLabelPO();
            labelPO.setReceiptBodyId(bodyPO.getReceiptBodyId());
            labelPOs.add(labelPO);
        }
        entryLabelMapper.batchInsert(labelPOs);

        RecordPO recordPO = new RecordPO();
        recordPO.setCreateTime(new Date());
        recordPO.setName("夏慧");
        recordPO.setUserId((long) 1);
        recordPO.setType((byte) 1);
        recordMapper.insert(recordPO);

        return new ResultVO(1000);
    }
}
