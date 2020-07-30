package com.smartindustry.outbound.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.outbound.dto.LogisticsRecordDTO;
import com.smartindustry.outbound.service.IMaterialOutboundService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/7/14 19:00
 * @description: 物料出库
 * @version: 1.0
 */
@RequestMapping("outbound")
@RestController
public class MaterialOutboundController {
    @Autowired
    private IMaterialOutboundService materialOutboundService;

    /**
     * 分页查询
     *
     * @return
     */
    @PostMapping("pageQuery")
    public ResultVO pageQuery(@RequestBody Map<String, Object> reqData) {
        return materialOutboundService.pageQuery(reqData);
    }

    @RequestMapping("detail")
    public ResultVO detail(@RequestParam(value = "oid") Long oId) {
        return materialOutboundService.detail(oId);
    }

    @RequestMapping("outbound")
    public ResultVO outbound(@RequestParam(value = "oid") Long oId) {
        return materialOutboundService.outbound(oId);
    }

    /**
     * 记录信息
     *
     * @param oId
     * @return
     */
    @RequestMapping("record")
    public ResultVO record(@RequestParam(value = "oid") Long oId) {
        return materialOutboundService.record(oId);
    }

    /**
     * 物流信息-新增/编辑
     *
     * @param dto
     * @return
     */
    @PostMapping("logEdit")
    public ResultVO logEdit(@RequestBody LogisticsRecordDTO dto) {
        return materialOutboundService.logEdit(dto);
    }

    /**
     * 上传
     *
     * @param file
     * @return
     */
    @PostMapping("upload")
    public ResultVO upload(@Param("file") MultipartFile file) {
        return materialOutboundService.upload(file);
    }

}
