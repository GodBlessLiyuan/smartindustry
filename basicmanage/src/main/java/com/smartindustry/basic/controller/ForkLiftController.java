package com.smartindustry.basic.controller;

import com.smartindustry.basic.dto.ForkLiftDTO;
import com.smartindustry.basic.service.IForkLiftService;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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

    /***
     * 叉车删除接口
     * @param ids
     * @return
     */
    @PostMapping("delete")
    public ResultVO delete(@RequestBody List<Long> ids){
        return forkLiftService.delete(ids);
    }

    /***
     * 查询叉车记录
     * @param dto
     * @return
     */
    @PostMapping("record")
    public ResultVO forkRecord(@RequestBody ForkLiftDTO dto){
        return forkLiftService.record(dto.getFid());
    }

    /***
     * 查询叉车详情
     * @param dto
     * @return
     */
    @PostMapping("detail")
    public ResultVO detail(@RequestBody ForkLiftDTO dto){
        return forkLiftService.detail(dto.getFid());
    }
}
