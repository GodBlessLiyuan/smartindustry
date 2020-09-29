package com.smartindustry.workbench.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.workbench.dto.OperateDTO;
import com.smartindustry.workbench.service.IWorkBenchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hui.feng
 * @date created in 2020/9/25
 * @description
 */
@RequestMapping("workbench")
@RestController
public class WorkBenchController {

    @Autowired
    private IWorkBenchService workBenchService;

    /**
     * 智能工作台  待办工作
     * @param dto
     * @return
     */
    @PostMapping("work")
    public ResultVO work(@RequestBody OperateDTO dto) {
        return workBenchService.work(dto);

    }

    /**
     * 智能工作台 快捷访问
     * @param dto
     * @return
     */
    @PostMapping("shortcut")
    public ResultVO shortcut(@RequestBody OperateDTO dto)  {
        return workBenchService.shortcut(dto);
    }
}
