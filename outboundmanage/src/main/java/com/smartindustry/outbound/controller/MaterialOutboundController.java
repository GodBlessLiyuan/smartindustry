package com.smartindustry.outbound.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.outbound.dto.LogisticsRecordDTO;
import com.smartindustry.outbound.dto.OperateDTO;
import com.smartindustry.outbound.service.IMaterialOutboundService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @PreAuthorize("@ss.hasAnyPermi('om:mo:woout:query,om:mo:fpout:query')")
    public ResultVO pageQuery(@RequestBody Map<String, Object> reqData) {
        return materialOutboundService.pageQuery(reqData);
    }

    @PostMapping("detail")
    @PreAuthorize("@ss.hasAnyPermi('om:mo:woout:queryinfo,om:mo:fpout:queryinfo,om:mo:woout:out,om:mo:fpout:out')")
    public ResultVO detail(@RequestBody OperateDTO dto) {
        return materialOutboundService.detail(dto);
    }

    @PostMapping("outbound")
    @PreAuthorize("@ss.hasAnyPermi('om:mo:woout:out,om:mo:fpout:out')")
    public ResultVO outbound(@RequestBody OperateDTO dto) {
        return materialOutboundService.outbound(dto);
    }

    /**
     * 记录信息
     *
     * @param dto
     * @return
     */
    @PostMapping("record")
    public ResultVO record(@RequestBody OperateDTO dto) {
        return materialOutboundService.record(dto);
    }

    /**
     * 物流信息-新增/编辑
     *
     * @param dto
     * @return
     */
    @PostMapping("logEdit")
    @PreAuthorize("@ss.hasAnyPermi('om:mo:woout:queryinfo,om:mo:fpout:queryinfo,om:mo:woout:out,om:mo:fpout:out')")
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
