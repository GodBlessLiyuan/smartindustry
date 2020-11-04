package com.smartindustry.pda.service;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.pda.dto.OutboundDTO;

import javax.servlet.http.HttpSession;

/**
 * @author: xiahui
 * @date: Created in 2020/10/29 9:27
 * @description: 成品出库
 * @version: 1.0
 */
public interface IOutboundService {
    ResultVO erp();

    ResultVO detail(HttpSession session, OutboundDTO dto);

    ResultVO execute(HttpSession session);
}
