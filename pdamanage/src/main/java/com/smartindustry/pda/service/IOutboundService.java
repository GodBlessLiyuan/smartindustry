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

    ResultVO online(HttpSession session, OutboundDTO dto);

    ResultVO list(HttpSession session, Byte type);

    ResultVO detail(HttpSession session, OutboundDTO dto);

    ResultVO execute(HttpSession session);

    ResultVO outbound(HttpSession session, OutboundDTO dto);
}
