package com.smartindustry.basic.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.basic.dto.OperateDTO;
import com.smartindustry.basic.service.IClientService;
import com.smartindustry.basic.vo.ClientVO;
import com.smartindustry.common.bo.si.ClientBO;
import com.smartindustry.common.mapper.si.ClientMapper;
import com.smartindustry.common.pojo.si.ClientPO;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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


    @Override
    public ResultVO pageQuery(Map<String, Object> reqData) {
        Page<Long> page = PageQueryUtil.startPage(reqData);
        List<ClientPO> bos = clientMapper.pageQuery(reqData);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), ClientVO.convert(bos)));
    }

    @Override
    public ResultVO query(OperateDTO dto) {
        ClientPO bo = clientMapper.selectByPrimaryKey(dto.getCid());
        return ResultVO.ok().setData(ClientVO.convert(bo));
    }
}
