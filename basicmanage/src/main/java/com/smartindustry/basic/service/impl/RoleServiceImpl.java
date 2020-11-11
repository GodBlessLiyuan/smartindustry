package com.smartindustry.basic.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.basic.service.IRoleService;
import com.smartindustry.basic.vo.RoleVO;
import com.smartindustry.common.mapper.am.RoleMapper;
import com.smartindustry.common.pojo.am.RolePO;
import com.smartindustry.common.security.service.TokenService;
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
 * @date: Created in 15:27 2020/7/31
 * @version: 1.0.0
 * @description:
 */
@EnableTransactionManagement
@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    TokenService tokenService;

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData){
        Page<RolePO> page = PageQueryUtil.startPage(reqData);
        List<RolePO> pos = roleMapper.rolePageQuery(reqData);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), RoleVO.convert(pos)));
    }

}
