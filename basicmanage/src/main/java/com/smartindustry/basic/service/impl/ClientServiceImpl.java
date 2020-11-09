package com.smartindustry.basic.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.basic.dto.ClientDTO;
import com.smartindustry.basic.dto.OperateDTO;
import com.smartindustry.basic.service.IClientService;
import com.smartindustry.basic.vo.ClientVO;
import com.smartindustry.common.bo.si.ClientBO;
import com.smartindustry.common.mapper.si.ClientMapper;
import com.smartindustry.common.pojo.si.ClientPO;
import com.smartindustry.common.security.service.TokenService;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
    private TokenService tokenService;

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData) {
        Page<Long> page = PageQueryUtil.startPage(reqData);
        List<ClientPO> bos = clientMapper.pageQuery(reqData);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), ClientVO.convert(bos)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO delete(List<Long> cids){
        clientMapper.batchDelete(cids);
        return ResultVO.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO edit(ClientDTO dto){
        // 客户代码唯一
        ClientPO existPO = clientMapper.queryNo(dto.getCno());
        if (null != existPO && !existPO.getClientId().equals(dto.getCid())) {
            return new ResultVO(1004);
        }
        if(null == dto.getCid()){
            ClientPO po = ClientDTO.createPO(dto);
            clientMapper.insert(po);
            return ResultVO.ok();
        }
        // 编辑
        ClientPO clientPO = clientMapper.selectByPrimaryKey(dto.getCid());
        if (null == clientPO) {
            return new ResultVO(1002);
        }
        ClientDTO.buildPO(clientPO, dto);
        clientPO.setUpdateTime(new Date());
        clientMapper.updateByPrimaryKeySelective(clientPO);
        return ResultVO.ok();
    }

    @Override
    public ResultVO query(OperateDTO dto){
        ClientBO bo = clientMapper.queryByCid(dto.getCid());
        return ResultVO.ok().setData(ClientVO.convert(bo));
    }
}
