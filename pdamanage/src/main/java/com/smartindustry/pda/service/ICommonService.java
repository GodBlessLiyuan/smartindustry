package com.smartindustry.pda.service;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.pda.dto.CommonDTO;

import javax.servlet.http.HttpSession;

/**
 * @author: xiahui
 * @date: Created in 2020/11/4 18:31
 * @description: 公共模块
 * @version: 1.0
 */
public interface ICommonService {
    ResultVO online(HttpSession session, CommonDTO dto);

    ResultVO list(HttpSession session, CommonDTO dto);

    ResultVO rfid(HttpSession session, CommonDTO dto);
}
