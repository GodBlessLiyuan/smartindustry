package com.smartindustry.storage.service;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.dto.LogisticsDTO;
import com.smartindustry.storage.dto.ReceiptDTO;

import java.util.List;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/6/23 11:38
 * @description: 收料管理
 * @version: 1.0
 */
public interface IReceiptManageService {
    /**
     * 分页查询
     *
     * @param pageNum
     * @param pageSize
     * @param reqData
     * @return
     */
    ResultVO pageQuery(int pageNum, int pageSize, Map<String, Object> reqData);

    /**
     * 新增
     *
     * @param dto
     * @return
     */
    ResultVO insert(ReceiptDTO dto);

    /**
     * 删除
     *
     * @param rbIds
     * @return
     */
    ResultVO delete(List<Long> rbIds);

    /**
     * 编辑物流信息
     *
     * @param dto
     * @return
     */
    ResultVO editLog(LogisticsDTO dto);

    /**
     * 查询操作记录
     *
     * @param rbId
     * @return
     */
    ResultVO record(Long rbId, Byte status);

    /**
     * 物料查询
     *
     * @param pageNum
     * @param pageSize
     * @param reqData
     * @return
     */
    ResultVO materialQuery(int pageNum, int pageSize, Map<String, Object> reqData);
}
