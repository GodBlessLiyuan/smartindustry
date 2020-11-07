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
    public ResultVO sync(){
        return basicService.material();
    }

    @PostMapping("client")
    public ResultVO client(){
        return basicService.client();
    }

    @PostMapping("supplier")
    public ResultVO supplier(){
        return basicService.supplier();
    }

    @PostMapping("dept")
    public ResultVO dept(){
        return basicService.dept();
    }

    @PostMapping("role")
    public ResultVO role(){
        return basicService.role();
    }

    @PostMapping("user")
    public ResultVO user(){
        return basicService.user();
    }

}
