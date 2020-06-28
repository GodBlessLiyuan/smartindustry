package com.smartindustry.storage.service;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.dto.LabelDTO;

/**
 * @author: xiahui
 * @date: Created in 2020/6/28 15:06
 * @description: 标签管理
 * @version: 1.0
 */
public interface ILabelManageService {
    ResultVO insert(LabelDTO dto);
}
