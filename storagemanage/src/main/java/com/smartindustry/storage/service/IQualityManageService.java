package com.smartindustry.storage.service;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.dto.IqcTestDTO;

/**
 * @author: xiahui
 * @date: Created in 2020/6/23 11:39
 * @description: 质量管理
 * @version: 1.0
 */
public interface IQualityManageService {

    /**
     * IQC检验
     *
     * @param dto
     * @return
     */
    ResultVO test(IqcTestDTO dto);

    /**
     * 查询记录
     *
     * @param rbId
     * @param status
     * @return
     */
    ResultVO record(Long rbId, Byte status);
}
