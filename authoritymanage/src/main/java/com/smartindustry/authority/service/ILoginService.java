package com.smartindustry.authority.service;

import com.smartindustry.authority.dto.LoginDTO;
import com.smartindustry.common.vo.ResultVO;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author: jiangzhaojie
 * @date: Created in 9:22 2020/8/3
 * @version: 1.0.0
 * @description:
 */
public interface ILoginService {
    /**
     * 登入
     * @param dto
     * @return
     */
    ResultVO login(HttpSession session,
                   HttpServletResponse response, LoginDTO dto);
}
