package com.smartindustry.storage.service;

import com.smartindustry.common.vo.ResultVO;

/**
 * @author: xiahui
 * @date: Created in 2020/6/23 11:39
 * @description: 质量管理
 * @version: 1.0
 */
public interface IQualityManageService {
    /**
     * 查询记录
     *
     * @param rbId
     * @param status
     * @return
     */
    ResultVO record(Long rbId, Byte status);
}
