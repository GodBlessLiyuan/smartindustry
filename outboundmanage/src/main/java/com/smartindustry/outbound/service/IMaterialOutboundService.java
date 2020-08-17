package com.smartindustry.outbound.service;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.outbound.dto.LogisticsRecordDTO;
import com.smartindustry.outbound.dto.OperateDTO;
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
     * @param dto
     * @return
     */
    ResultVO detail(OperateDTO dto);

    /**
     * 出库
     *
     * @param dto
     * @return
     */
    ResultVO outbound(OperateDTO dto);

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
     * @param dto
     * @return
     */
    ResultVO record(OperateDTO dto);


}
