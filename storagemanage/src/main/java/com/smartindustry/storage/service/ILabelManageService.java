package com.smartindustry.storage.service;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.dto.PrintLabelDTO;

/**
 * @author: xiahui
 * @date: Created in 2020/6/28 15:06
 * @description: 标签管理
 * @version: 1.0
 */
public interface ILabelManageService {
    /**
     * 查询
     *
     * @param rbId
     * @return
     */
    ResultVO query(Long rbId);

    /**
     * 标签录入
     *
     * @param dto
     * @return
     */
    ResultVO insert(PrintLabelDTO dto);

    /**
     * 删除
     *
     * @param plId
     * @return
     */
    ResultVO delete(Long rbId, Long plId);

    /**
     * 录入完成
     *
     * @param rbId
     * @return
     */
    ResultVO finish(Long rbId);
}
