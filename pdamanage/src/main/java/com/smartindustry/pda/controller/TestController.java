package com.smartindustry.pda.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.pda.service.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 18:48 2020/11/16
 * @ Description：测试类
 * @ Modified By：
 * @ Version:     1.0
 */
@RequestMapping("test")
@RestController
public class TestController {
    @Autowired
    private IStorageService storageService;

    /**
     * 警告测试
     *
     * @return
     */
    @PostMapping("twarn")
    public ResultVO testWarn(@RequestBody Map map) {
        String imei = null;
        if (map.get("imei") == null) {
            imei = null;
        } else {
            imei = map.get("imei").toString();
        }
        return storageService.testWarn(new Byte(map.get("type").toString()), imei);
    }
}
