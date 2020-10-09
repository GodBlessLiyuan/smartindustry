package com.smartindustry.workbench.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.workbench.service.IBigDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: xiahui
 * @date: Created in 2020/9/27 18:58
 * @description: 数据统计
 * @version: 1.0
 */
@RequestMapping("bd")
@RestController
public class BigDataController {
    @Autowired
    private IBigDataService bigDataService;

    Logger logger = LoggerFactory.getLogger(BigDataController.class);

    /**
     * WMS
     *
     * @return
     */
    @PostMapping("wms")
    public ResultVO wms() {
        logger.info("workbench - wms");
        return bigDataService.wms();
    }
}
