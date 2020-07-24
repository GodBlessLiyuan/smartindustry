package com.smartindustry.outbound.service;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.outbound.dto.LogisticsRecordDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/7/14 19:01
 * @description: 物料出库
 * @version: 1.0
 */
public interface IMaterialOutboundService {
    /**
     * 分页查询
     *
     * @param reqData
     * @return
     */
    ResultVO pageQuery(Map<String, Object> reqData);

    /**
     * 详情
     *
     * @param oId
     * @return
     */
    ResultVO detail(Long oId);

    /**
     * 出库
     *
     * @param oId
     * @return
     */
    ResultVO outbound(Long oId);

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    ResultVO upload(MultipartFile file);

    /**
     * 物流信息-新增
     *
     * @param dto
     * @return
     */
    ResultVO logEdit(LogisticsRecordDTO dto);

    /**
     * 记录信息
     *
     * @param oId
     * @return
     */
    ResultVO record(Long oId);


}
