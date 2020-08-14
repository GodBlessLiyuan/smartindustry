package com.smartindustry.inventory.service;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.inventory.dto.MaterialDetailDTO;

import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/8/10 16:04
 * @description: 物料库存明细查询
 * @version: 1.0
 */
public interface IMaterialDetailService {
    ResultVO pageQuery(Map<String, Object> reqData);

    ResultVO lock(MaterialDetailDTO dto);
}
