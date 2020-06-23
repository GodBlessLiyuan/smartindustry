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
    ResultVO pageQuery(int pageNum, int pageSize, Map<String, Object> reqData);

    ResultVO insert(ReceiptDTO dto);
}
