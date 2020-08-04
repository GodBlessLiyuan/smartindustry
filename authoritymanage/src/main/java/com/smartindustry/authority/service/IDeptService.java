package com.smartindustry.authority.service;

import com.smartindustry.authority.dto.DeptDTO;
import com.smartindustry.authority.dto.OperateDTO;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 9:23 2020/7/30
 * @version: 1.0.0
 * @description:
 */
public interface IDeptService {

    /**
     * 分页模糊查询部门信息
     * @param reqData
     * @return
     */
    ResultVO pageQuery(Map<String, Object> reqData);

    /**
     * 批量禁用部门
     * @param dtos
     * @return
     */
    ResultVO batchUpdate(List<OperateDTO> dtos);

    /**
     * 新增部门
     * @param dto
     * @return
     */
    ResultVO insert(DeptDTO dto);

    /**
     * 修改部门
     * @param dto
     * @return
     */
    ResultVO update(DeptDTO dto);

    /**
     * 批量删除
     * @param dtos
     * @return
     */
    ResultVO delete(List<Long> dtos);

    /**
     * 按照树形结构给前端传值部门名字
     * @return
     */
    ResultVO queryDeptName();

    /**
     * 给出部门负责人列表
     * @return
     */
    ResultVO queryLeader(OperateDTO dto);
}
