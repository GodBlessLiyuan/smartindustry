package com.smartindustry.storage.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.dto.ForkLiftDTO;
import com.smartindustry.storage.service.IForkLiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 14:13 2020/10/29
 * @version: 1.0.0
 * @description:
 */
@RequestMapping("forklift")
@RestController
public class ForkLiftController {
    @Autowired
    private IForkLiftService forkLiftService;

    /**
     * 分页查询
     *
     * @return
     */
    @PostMapping("pageQuery")
    public ResultVO pageQuery(@RequestBody Map<String, Object> reqData) {
        return forkLiftService.pageQuery(reqData);
    }

    /**
     * 采购入库单编辑
     * @param dto
     * @return
     */
    @PostMapping("edit")
    public ResultVO edit(@RequestBody ForkLiftDTO dto){
        return forkLiftService.edit(dto);
    }
}
