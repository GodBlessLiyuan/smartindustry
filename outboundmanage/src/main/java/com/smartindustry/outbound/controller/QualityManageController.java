package com.smartindustry.outbound.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.outbound.service.IQualityManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: xiahui
 * @date: Created in 2020/7/14 18:59
 * @description: 质量管理
 * @version: 1.0
 */
@RequestMapping("quality")
@RestController
public class QualityManageController {
    @Autowired
    private IQualityManageService qualityManageService;

    @RequestMapping("pickOqcButton")
    public ResultVO pickOqcButton(@RequestParam(value = "phid", required = false) Long pickHeadId){
        return qualityManageService.pickOqcButton(pickHeadId);
    }
}
