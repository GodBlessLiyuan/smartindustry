package com.smartindustry.authority.controller;

import com.smartindustry.authority.dto.DeptDTO;
import com.smartindustry.authority.dto.OperateDTO;
import com.smartindustry.authority.service.IDeptService;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 9:21 2020/7/30
 * @version: 1.0.0
 * @description:
 */
@RequestMapping("dept")
@RestController
public class DeptController {
    @Autowired
    private IDeptService deptService;


    /**
     * 对部门信息进行分页模糊查询
     * @param reqData
     * @return
     */
    @PostMapping("pageQuery")
    @PreAuthorize("@ss.hasPermi('am:dept:query')")
    public ResultVO pageQuery(@RequestBody Map<String, Object> reqData) {
        return deptService.pageQuery(reqData);
    }

    @PostMapping("status")
    @PreAuthorize("@ss.hasAnyPermi('am:dept:disable,am:dept:enable')")
    public ResultVO status(@RequestBody List<OperateDTO> dtos) {
        return deptService.batchUpdate(dtos);
    }

    @PostMapping("insert")
    @PreAuthorize("@ss.hasPermi('am:dept:insert')")
    public ResultVO insert(@RequestBody DeptDTO dto) {
        return deptService.insert(dto);
    }

    @PostMapping("update")
    @PreAuthorize("@ss.hasPermi('am:dept:update')")
    public ResultVO update(@RequestBody DeptDTO dto) {
        return deptService.update(dto);
    }

    @PostMapping("delete")
    @PreAuthorize("@ss.hasPermi('am:dept:delete')")
    public ResultVO delete(@RequestBody List<Long> dids) {
        return deptService.delete(dids);
    }

    @PostMapping("queryDeptName")
    public ResultVO queryDeptName() {
        return deptService.queryDeptName();
    }

    @PostMapping("chooseLeader")
    public ResultVO queryLeader(OperateDTO dto) {
        return deptService.queryLeader(dto);
    }

    @PostMapping("queryDeptRecord")
    public ResultVO queryDeptRecord(@RequestBody Map<String, Object> reqData) {
        return deptService.queryDeptRecord(reqData);
    }
}
