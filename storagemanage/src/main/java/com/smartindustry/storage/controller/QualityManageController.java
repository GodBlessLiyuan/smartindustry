package com.smartindustry.storage.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.dto.IqcTestDTO;
import com.smartindustry.storage.service.IQualityManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: xiahui
 * @date: Created in 2020/6/23 11:39
 * @description: 质量管理
 * @version: 1.0
 */
@RequestMapping("quality")
@RestController
public class QualityManageController {
    @Autowired
    private IQualityManageService qualityManageService;

    @RequestMapping("test")
    public ResultVO test(@RequestBody IqcTestDTO dto) {
        return qualityManageService.test(dto);
    }

    @RequestMapping("record")
    public ResultVO record(@RequestParam(value = "rbId") Long rbId, @RequestParam(value = "status", required = false, defaultValue = "1") Byte status) {
        return qualityManageService.record(rbId, status);
    }
}
