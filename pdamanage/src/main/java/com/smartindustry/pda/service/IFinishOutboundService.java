package com.smartindustry.pda.service;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.pda.FinishOutboundDTO;

import javax.servlet.http.HttpSession;

/**
 * @author: xiahui
 * @date: Created in 2020/10/29 9:27
 * @description: 成品出库
 * @version: 1.0
 */
public interface IFinishOutboundService {
    ResultVO erp();

    ResultVO online(HttpSession session, FinishOutboundDTO dto);

    ResultVO list(HttpSession session, FinishOutboundDTO dto);
}
