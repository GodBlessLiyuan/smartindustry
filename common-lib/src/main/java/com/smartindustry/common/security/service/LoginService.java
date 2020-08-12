package com.smartindustry.common.security.service;

import com.smartindustry.common.bo.am.LoginUserBO;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author: jiangzhaojie
 * @date: Created in 9:23 2020/8/3
 * @version: 1.0.0
 * @description:
 */
@Service
public class LoginService{

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    public ResultVO login(HttpSession session,
                        HttpServletResponse response,String username,String pawwsord,String checkCode){
        // 得到验证码
        String code = (String) session.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        if (null == code) {
            // 验证码过期
            return new ResultVO(1010);
        }
        if (!code.equalsIgnoreCase(checkCode)) {
            //验证码错误
            return new ResultVO(1011);
        }
        // 用户验证
        Authentication authentication = null;
        // 生成token  该方法会去调用UserDetailsServiceImpl.loadUserByUsername
        try{
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username,pawwsord));
        }catch (Exception e){
            if (e instanceof BadCredentialsException) {
                //输入密码错误
                return new ResultVO(1012);
            }
        }
        LoginUserBO user = (LoginUserBO) authentication.getPrincipal();
        Map<String,Object> map = new HashMap<>();
        map.put("token",tokenService.createToken(user));
        map.put("authAndUser",user);
        return ResultVO.ok().setData(map);
    }
}
