package com.smartindustry.authority.service.impl;

import com.google.code.kaptcha.Constants;
import com.smartindustry.authority.dto.LoginDTO;
import com.smartindustry.authority.service.ILoginService;
import com.smartindustry.authority.dto.LoginUserDTO;
import com.smartindustry.authority.service.TokenService;
import com.smartindustry.common.mapper.am.UserMapper;
import org.springframework.security.core.Authentication;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author: jiangzhaojie
 * @date: Created in 9:23 2020/8/3
 * @version: 1.0.0
 * @description:
 */
@Service
public class LoginServiceImpl implements ILoginService {
    @Autowired
    UserMapper userMapper;
    @Resource
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @Override
    public ResultVO login(HttpSession session,
                        HttpServletResponse response,LoginDTO dto){
        // 得到验证码
        String code = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (null == code) {
            // 验证码过期
            return new ResultVO(2102);
        }
        if (!code.equalsIgnoreCase(dto.getCode())) {
            //验证码错误
            return new ResultVO(2103);
        }
        // 用户验证
        Authentication authentication = null;
        // 生成token
        authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        LoginUserDTO user = (LoginUserDTO) authentication.getPrincipal();
        return ResultVO.ok().setData(tokenService.createToken(user));
    }



}
