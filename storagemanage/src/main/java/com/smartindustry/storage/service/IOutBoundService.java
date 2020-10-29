package com.smartindustry.storage.service;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.dto.OperateDTO;
import com.smartindustry.storage.dto.OutboundHeadDTO;

import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 9:26 2020/10/28
 * @version: 1.0.0
 * @description:
 */
public interface IOutBoundService {
    /**
     * 工单出库单的分页查询
     * @param reqData
     * @return
     */
    ResultVO pageQuery(Map<String, Object> reqData);

    /**
     * 生成混料工单号
     * @return
     */
    ResultVO queryMix();

    /**
     * 新增/编辑
     * @param dto
     * @return
     */
    ResultVO edit(OutboundHeadDTO dto);

    /**
     * 根据出库单id查询出库详情
     * @param dto
     * @return
     */
    ResultVO detail(OperateDTO dto);


    /**
     * 根据操作人查询出库操作记录
     * @param dto
     * @return
     */
    ResultVO queryOutboundRecord(OperateDTO dto);
}
