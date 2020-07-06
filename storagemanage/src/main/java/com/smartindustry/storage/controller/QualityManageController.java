package com.smartindustry.storage.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.constant.ReceiptConstant;
import com.smartindustry.storage.dto.TestDTO;
import com.smartindustry.storage.dto.QeConfirmDTO;
import com.smartindustry.storage.service.IQualityManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("pageQuery")
    public ResultVO pageQuery(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                              @RequestParam(value = "rno", required = false, defaultValue = "") String rno,
                              @RequestParam(value = "type", required = false, defaultValue = "0") Byte type,
                              @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                              @RequestParam(value = "qaStatus", required = false, defaultValue = "0") Byte qaStatus,
                              @RequestParam(value = "status") Byte status) {
        if (!ReceiptConstant.RECEIPT_IQC_DETECT.equals(status) && !ReceiptConstant.RECEIPT_QE_DETECT.equals(status) && !ReceiptConstant.RECEIPT_QE_CONFIRM.equals(status)) {
            return new ResultVO(2000);
        }

        Map<String, Object> reqData = new HashMap<>(8);
        // 质量管理 分页查询查询
        reqData.put("qa", true);
        reqData.put("rno", rno);
        reqData.put("type", type);
        reqData.put("keyword", keyword);
        reqData.put("qaStatus", qaStatus);
        reqData.put("status", status);
        return qualityManageService.pageQuery(pageNum, pageSize, reqData);
    }

    @RequestMapping("iqcTest")
    public ResultVO iqcTest(@RequestBody TestDTO dto) {
        return qualityManageService.iqcTest(dto);
    }

    @RequestMapping("qeConfirm")
    public ResultVO qeConfirm(@RequestBody QeConfirmDTO dto) {
        return qualityManageService.qeConfirm(dto);
    }

    @RequestMapping("storage")
    public ResultVO storage(@RequestParam(value = "rbid") Long rbid) {
        return qualityManageService.storage(rbid);
    }

    @RequestMapping("record")
    public ResultVO record(@RequestParam(value = "rbid") Long rbid, @RequestParam(value = "status", required = false, defaultValue = "1") Byte status) {
        return qualityManageService.record(rbid, status);
    }
}
