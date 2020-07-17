package com.smartindustry.outbound.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.outbound.dto.LogisticsRecordDTO;
import com.smartindustry.outbound.service.IMaterialOutboundService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
     * @param pageNum  页号
     * @param pageSize 页大小
     * @param ono      出库单号
     * @param pno      拣货单号
     * @param cproject 对应项目
     * @param status   出库状态
     * @param otype    出库单类型
     * @return
     */
    @RequestMapping("pageQuery")
    public ResultVO pageQuery(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                              @RequestParam(value = "ono", required = false, defaultValue = "") String ono,
                              @RequestParam(value = "pno", required = false, defaultValue = "") String pno,
                              @RequestParam(value = "cproject", required = false, defaultValue = "") String cproject,
                              @RequestParam(value = "status", required = false, defaultValue = "0") Byte status,
                              @RequestParam(value = "otype") Byte otype) {
        Map<String, Object> reqData = new HashMap<>(5);
        reqData.put("ono", ono);
        reqData.put("pno", pno);
        reqData.put("cproject", cproject);
        reqData.put("status", status);
        reqData.put("otype", otype);

        return materialOutboundService.pageQuery(pageNum, pageSize, reqData);
    }

    @RequestMapping("detail")
    public ResultVO detail(@RequestParam(value = "oid") Long oId) {
        return materialOutboundService.detail(oId);
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
     * 物流信息-新增
     *
     * @param dto
     * @return
     */
    @RequestMapping("logEdit")
    public ResultVO logEdit(@RequestBody LogisticsRecordDTO dto) {
        return materialOutboundService.logEdit(dto);
    }

    /**
     * 上传
     *
     * @param file
     * @return
     */
    @RequestMapping("upload")
    public ResultVO upload(@Param("file") MultipartFile file) {
        return materialOutboundService.upload(file);
    }
}
