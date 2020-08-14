package com.smartindustry.authority.service;

import com.smartindustry.authority.dto.OperateDTO;
import com.smartindustry.authority.dto.RoleDTO;
import com.smartindustry.authority.dto.UserDTO;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 15:27 2020/7/31
 * @version: 1.0.0
 * @description:
 */
public interface IRoleService {
    /**
     * 角色分页查询
     * @param reqData
     * @return
     */
    ResultVO pageQuery(Map<String, Object> reqData);

    /**
     * 批量更新角色状态
     * @param dtos
     * @return
     */
    ResultVO batchUpdate(List<OperateDTO> dtos);


    /**
     * 批量删除角色
     * @param rids
     * @return
     */
    ResultVO delete(List<Long> rids);

    /**
     * 新增角色
     * @param dto
     * @return
     */
    ResultVO insert(RoleDTO dto);

    /**
     * 更新用户
     * @param dto
     * @return
     */
    ResultVO update(RoleDTO dto);

    /**
     * 查询所有的菜单结构
     * @return
     */
    ResultVO queryAllMenu() ;

    /**
     * 查询所有的按钮权限结构
     * @return
     */
    ResultVO queryAllButton();

    /**
     * 根据roleid和权限id列表更新角色权限表
     * @param dto
     * @return
     */
    ResultVO updatePerms(OperateDTO dto);

    /**
     * 根据角色id查找所有权限
     * @param dto
     * @return
     */
    ResultVO queryRolePerms(OperateDTO dto);

    /**
     * 根据操作人查询角色记录表
     * @param reqData
     * @return
     */
    ResultVO queryRoleRecord(Map<String, Object> reqData);

}
