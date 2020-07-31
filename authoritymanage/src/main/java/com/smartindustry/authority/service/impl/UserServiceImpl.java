package com.smartindustry.authority.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.authority.dto.DeptDTO;
import com.smartindustry.authority.dto.OperateDTO;
import com.smartindustry.authority.dto.UserDTO;
import com.smartindustry.authority.service.IUserService;
import com.smartindustry.authority.vo.DeptVO;
import com.smartindustry.authority.vo.RoleVO;
import com.smartindustry.authority.vo.UserVO;
import com.smartindustry.common.bo.am.DeptBO;
import com.smartindustry.common.bo.am.UserBO;
import com.smartindustry.common.mapper.am.RoleMapper;
import com.smartindustry.common.mapper.am.UserMapper;
import com.smartindustry.common.pojo.am.DeptPO;
import com.smartindustry.common.pojo.am.RolePO;
import com.smartindustry.common.pojo.am.UserPO;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    UserMapper userMapper;
    @Autowired
    RoleMapper roleMapper;

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData){
        Page<UserBO> page = PageQueryUtil.startPage(reqData);
        List<UserBO> bos = userMapper.userPageQuery(reqData);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), UserVO.convert(bos)));
    }

    @Override
    public ResultVO batchUpdate(List<OperateDTO> dtos){
        List<UserPO> pos = UserDTO.updateList(dtos);
        userMapper.updateBatch(pos);
        return ResultVO.ok();
    }

    @Override
    public ResultVO batchDelete(List<OperateDTO> dtos){
        List<UserPO> pos = UserDTO.updateList(dtos);
        userMapper.deleteBatch(pos);
        return ResultVO.ok();
    }

    @Override
    public ResultVO insert(UserDTO dto){
        UserPO po = UserDTO.createPO(dto);
        userMapper.insert(po);
        return ResultVO.ok();
    }

    @Override
    public ResultVO update(UserDTO dto){
        UserPO po = UserDTO.createPO(dto);
        userMapper.updateByPrimaryKeySelective(po);
        return ResultVO.ok();
    }

    @Override
    public ResultVO updatePassword(OperateDTO dto){
        UserPO po = UserDTO.buildPO(dto);
        userMapper.updateByPrimaryKeySelective(po);
        return ResultVO.ok();
    }

    @Override
    public ResultVO queryRole(OperateDTO dto){
        List<RolePO> pos = roleMapper.selectAll(dto.getRname());
        return ResultVO.ok().setData(RoleVO.convert(pos));
    }


}
