package com.smartindustry.pda.service;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.pda.dto.OutboundDTO;

import javax.servlet.http.HttpSession;

/**
 * @author: xiahui
 * @date: Created in 2020/11/4 18:31
 * @description: 公共模块
 * @version: 1.0
 */
public interface ICommonService {
    ResultVO online(HttpSession session, OutboundDTO dto);

    ResultVO list(HttpSession session, Byte type);
}
