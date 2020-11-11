package com.smartindustry.basic.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.basic.service.IUserService;
import com.smartindustry.basic.vo.UserVO;
import com.smartindustry.common.bo.am.UserBO;
import com.smartindustry.common.mapper.am.DeptMapper;
import com.smartindustry.common.mapper.am.UserMapper;
import com.smartindustry.common.pojo.am.DeptPO;
import com.smartindustry.common.security.service.TokenService;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * @author: jiangzhaojie
 * @date: Created in 18:53 2020/7/30
 * @version: 1.0.0
 * @description:
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    TokenService tokenService;
    @Autowired
    private DeptMapper deptMapper;
    @Override
    public ResultVO pageQuery(Map<String, Object> reqData) {
        Page<UserBO> page = PageQueryUtil.startPage(reqData);
        List<UserBO> bos = userMapper.userPageQuery(reqData);
        List<UserVO> vos = UserVO.convert(bos);
        for (UserVO vo : vos) {
            List<Long> deptIds = getDeptLevel(vo.getDid());
            Collections.reverse(deptIds);
            vo.setDcode(deptIds);
        }
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), vos));
    }

    /**
     * 根据当前的部门id,获得父节点列表
     *
     * @param deptId
     * @return
     */
    private List<Long> getDeptLevel(Long deptId) {
        List<Long> deptIds = new ArrayList<>();
        while (deptId != null) {
            DeptPO po = deptMapper.selectByPrimaryKey(deptId);
            deptIds.add(po.getDeptId());
            deptId = po.getParentId();
        }
        return deptIds;
    }
}
