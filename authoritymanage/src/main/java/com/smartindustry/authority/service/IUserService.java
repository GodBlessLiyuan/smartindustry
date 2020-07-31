package com.smartindustry.authority.service;

import com.smartindustry.authority.dto.DeptDTO;
import com.smartindustry.authority.dto.OperateDTO;
import com.smartindustry.authority.dto.UserDTO;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 18:53 2020/7/30
 * @version: 1.0.0
 * @description:
 */
public interface IUserService {
    /**
     * 用户的分页模糊查询
     * @param reqData
     * @return
     */
    ResultVO pageQuery(Map<String, Object> reqData);

    /**
     * 批量禁用用户
     * @param dtos
     * @return
     */
    ResultVO batchUpdate(List<OperateDTO> dtos);


    /**
     * 批量删除
     * @param dtos
     * @return
     */
    ResultVO batchDelete(List<OperateDTO> dtos);

    /**
     * 新增用户
     * @param dto
     * @return
     */
    ResultVO insert(UserDTO dto);

    /**
     * 更新用户
     * @param dto
     * @return
     */
    ResultVO update(UserDTO dto);

    /**
     * 更新用户密码
     * @param dto
     * @return
     */
    ResultVO updatePassword(OperateDTO dto);

    /**
     * 查询所有的角色名称
     * @param dto
     * @return
     */
    ResultVO queryRole(OperateDTO dto);
}
