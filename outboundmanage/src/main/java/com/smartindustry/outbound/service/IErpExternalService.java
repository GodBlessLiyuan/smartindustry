package com.smartindustry.outbound.service;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.outbound.dto.PickDTO;

/**
 * @author: xiahui
 * @date: Created in 2020/7/15 15:11
 * @description: ERP 外部接口
 * @version: 1.0
 */
public interface IErpExternalService {
    /**
     * ERP 传递 拣货单
     *
     * @param dto
     * @return
     */
    ResultVO pick(PickDTO dto);
}
