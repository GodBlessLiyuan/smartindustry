package com.smartindustry.storage.service.impl;

import com.smartindustry.common.mapper.PrintLabelMapper;
import com.smartindustry.common.mapper.RecordMapper;
import com.smartindustry.common.pojo.PrintLabelPO;
import com.smartindustry.common.pojo.RecordPO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.service.IQualityManageService;
import com.smartindustry.storage.vo.PrintLabelVO;
import com.smartindustry.storage.vo.RecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/6/23 11:39
 * @description: 质量管理
 * @version: 1.0
 */
@Service
public class QualityManageServiceImpl implements IQualityManageService {
    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private PrintLabelMapper printLabelMapper;

    @Override
    public ResultVO record(Long rbId, Byte status) {
        Map<String, Object> res = new HashMap<>();
        // 打印标签
        List<PrintLabelPO> printLabelPOs = printLabelMapper.queryByReceiptBodyId(rbId);
        res.put("print", PrintLabelVO.convert(printLabelPOs));
        // 操作记录
        List<RecordPO> recordPOs = recordMapper.queryByReceiptBodyId(rbId, status);
        res.put("record", RecordVO.convert(recordPOs));

        return new ResultVO<>(1000, res);
    }
}
