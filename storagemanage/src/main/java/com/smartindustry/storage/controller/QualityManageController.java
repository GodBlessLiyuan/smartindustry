package com.smartindustry.storage.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.constant.ReceiptConstant;
import com.smartindustry.storage.dto.IqcTestDTO;
import com.smartindustry.storage.dto.OperateDTO;
import com.smartindustry.storage.dto.QeConfirmDTO;
import com.smartindustry.storage.dto.QeTestDTO;
import com.smartindustry.storage.service.IQualityManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    @PostMapping("pageQuery")
    @PreAuthorize("@ss.hasAnyPermi('sm:qm:iqctest:query,sm:qm:iqcqe:query,sm:qm:qetest:query')")
    public ResultVO pageQuery(@RequestBody Map<String, Object> reqData) {
        return qualityManageService.pageQuery(reqData);
    }

    @PostMapping("iqcTest")
    @PreAuthorize("@ss.hasAnyPermi('sm:qm:iqctest:iqc')")
    public ResultVO iqcTest(@RequestBody IqcTestDTO dto) {
        return qualityManageService.iqcTest(dto);
    }

    @PostMapping("qeConfirm")
    @PreAuthorize("@ss.hasPermi('sm:qm:iqcqe:qeconfirm')")
    public ResultVO qeConfirm(@RequestBody QeConfirmDTO dto) {
        return qualityManageService.qeConfirm(dto);
    }

    @PostMapping("qeTest")
    @PreAuthorize("@ss.hasPermi('sm:qm:qetest:qetest')")
    public ResultVO qeTest(@RequestBody QeTestDTO dto) {
        return qualityManageService.qeTest(dto);
    }

    @PostMapping("storage")
    @PreAuthorize("@ss.hasAnyPermi('sm:qm:iqctest:storage,sm:qm:iqcqe:storage,sm:qm:qetest:storage')")
    public ResultVO storage(@RequestBody OperateDTO dto) {
        return qualityManageService.storage(dto);
    }

    @PostMapping("record")
    public ResultVO record(@RequestBody OperateDTO dto) {
        return qualityManageService.record(dto);
    }
}
