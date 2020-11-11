package com.smartindustry.basic.service;

import com.smartindustry.basic.dto.OperateDTO;
import com.smartindustry.common.vo.ResultVO;

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
     * 查询所有系统的所有权限
     * @return
     */
    ResultVO queryAuthority();

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
