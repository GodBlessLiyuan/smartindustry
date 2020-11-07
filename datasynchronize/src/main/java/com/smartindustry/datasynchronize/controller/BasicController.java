package com.smartindustry.datasynchronize.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.datasynchronize.service.IBasicService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author hui.feng
 * @date created in 2020/11/5
 * @description
 */
@RestController
@RequestMapping("basic")
public class BasicController {

    @Resource
    private IBasicService basicService;

    @PostMapping("material")
    public ResultVO sync(@RequestBody Map<String, Object> reqData){
        return basicService.material(reqData);
    }

    @PostMapping("client")
    public ResultVO client(@RequestBody Map<String, Object> reqData){
        return basicService.client(reqData);
    }

    @PostMapping("supplier")
    public ResultVO supplier(@RequestBody Map<String, Object> reqData){
        return basicService.supplier(reqData);
    }

    @PostMapping("dept")
    public ResultVO dept(@RequestBody Map<String, Object> reqData){
        return basicService.dept(reqData);
    }

    @PostMapping("role")
    public ResultVO role(@RequestBody Map<String, Object> reqData){
        return basicService.role(reqData);
    }

    @PostMapping("user")
    public ResultVO user(@RequestBody Map<String, Object> reqData){
        return basicService.user(reqData);
    }

}
