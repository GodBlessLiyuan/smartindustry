package com.smartindustry.bigdata.controller;

import com.smartindustry.bigdata.service.IBigDataService;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: xiahui
 * @date: Created in 2020/10/9 10:04
 * @description: TODO
 * @version: 1.0
 */
@RestController
public class BigDataController {
    @Autowired
    private IBigDataService bigDataService;

    @PostMapping("wms")
    public ResultVO wms() {
        return bigDataService.wms();
    }
}
