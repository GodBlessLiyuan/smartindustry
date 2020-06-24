package com.smartindustry.storage.service;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.dto.ReceiptDTO;

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
     * 查询操作记录
     *
     * @param rbId
     * @param order
     * @return
     */
    ResultVO record(Long rbId, byte order);
}
