package com.smartindustry.workbench.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.workbench.dto.OperateDTO;
import com.smartindustry.workbench.service.IWorkBenchService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping("work")
    public ResultVO work(@RequestBody OperateDTO dto) {
        return workBenchService.work(dto);

    }

    @RequestMapping("shortcut")
    public ResultVO shortcut(@RequestBody OperateDTO dto)  {
        return workBenchService.shortcut(dto);
    }
}
