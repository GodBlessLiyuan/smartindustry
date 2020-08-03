package com.smartindustry.authority.service;

import com.smartindustry.authority.dto.OperateDTO;
import com.smartindustry.authority.dto.RoleDTO;
import com.smartindustry.authority.dto.UserDTO;
import com.smartindustry.common.vo.ResultVO;

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
     * @param dtos
     * @return
     */
    ResultVO batchDelete(List<OperateDTO> dtos);

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
}
