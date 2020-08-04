package com.smartindustry.authority.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.authority.dto.DeptDTO;
import com.smartindustry.authority.dto.OperateDTO;
import com.smartindustry.authority.dto.RoleDTO;
import com.smartindustry.authority.service.IRoleService;
import com.smartindustry.authority.vo.DeptVO;
import com.smartindustry.authority.vo.RoleVO;
import com.smartindustry.common.bo.am.DeptBO;
import com.smartindustry.common.mapper.am.RoleMapper;
import com.smartindustry.common.pojo.am.DeptPO;
import com.smartindustry.common.pojo.am.RolePO;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 15:27 2020/7/31
 * @version: 1.0.0
 * @description:
 */
@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    RoleMapper roleMapper;

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData){
        Page<RolePO> page = PageQueryUtil.startPage(reqData);
        List<RolePO> pos = roleMapper.rolePageQuery(reqData);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), RoleVO.convert(pos)));
    }

    @Override
    public ResultVO batchUpdate(List<OperateDTO> dtos){
        List<RolePO> pos = RoleDTO.updateList(dtos);
        roleMapper.updateBatch(pos);
        return ResultVO.ok();
    }

    @Override
    public ResultVO insert(RoleDTO dto){

        RolePO po = RoleDTO.createPO(dto);
        roleMapper.insert(po);
        return ResultVO.ok();
    }

    @Override
    public ResultVO update(RoleDTO dto){
        RolePO po = RoleDTO.createPO(dto);
        roleMapper.updateByPrimaryKeySelective(po);
        return ResultVO.ok();
    }

    @Override
    public ResultVO delete(List<Long> rids){
        roleMapper.deleteBatch(rids);
        return ResultVO.ok();
    }
}
