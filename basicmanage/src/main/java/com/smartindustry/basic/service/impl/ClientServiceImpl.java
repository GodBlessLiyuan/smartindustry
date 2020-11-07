package com.smartindustry.basic.service.impl;

import com.github.pagehelper.Page;

import com.smartindustry.basic.dto.OperateDTO;
import com.smartindustry.basic.service.IClientService;
import com.smartindustry.basic.vo.ClientRecordVO;
import com.smartindustry.basic.vo.ClientVO;
import com.smartindustry.common.bo.si.ClientBO;
import com.smartindustry.common.bo.si.ClientRecordBO;
import com.smartindustry.common.mapper.si.ClientMapper;
import com.smartindustry.common.mapper.si.ClientRecordMapper;

import com.smartindustry.common.pojo.si.ClientPO;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 14:35 2020/9/16
 * @version: 1.0.0
 * @description:
 */
@EnableTransactionManagement
@Service
public class ClientServiceImpl implements IClientService {
    @Autowired
    private ClientMapper clientMapper;
    @Autowired
    private ClientRecordMapper clientRecordMapper;


    @Override
    public ResultVO pageQuery(Map<String, Object> reqData) {
        Page<Long> page = PageQueryUtil.startPage(reqData);
        List<ClientPO> bos = clientMapper.pageQuery(reqData);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), ClientVO.convert(bos)));
    }

    @Override
    public ResultVO queryRecord(OperateDTO dto) {
        List<ClientRecordBO> bos = clientRecordMapper.queryByCid(dto.getCid());
        Map<String, Object> map = new HashMap<>();
        map.put("record", bos.isEmpty() ? null : ClientRecordVO.convert(bos));
        return ResultVO.ok().setData(map);
    }

    @Override
    public ResultVO query(OperateDTO dto) {
        ClientBO bo = clientMapper.queryByCid(dto.getCid());
        return ResultVO.ok().setData(ClientVO.convert(bo));
    }
}
