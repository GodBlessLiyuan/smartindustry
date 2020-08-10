package com.smartindustry.authority.security.handle;

import com.alibaba.fastjson.JSON;
import com.smartindustry.authority.dto.LoginUserDTO;
import com.smartindustry.authority.service.TokenService;
import com.smartindustry.authority.util.ServletUtils;
import com.smartindustry.authority.util.StringUtils;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义退出处理类 返回成功
 * 
 * @author ruoyi
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler
{
    @Autowired
    private TokenService tokenService;

    /**
     * 退出处理
     * 
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException
    {
        LoginUserDTO loginUserDTO = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUserDTO))
        {
            String userName = loginUserDTO.getUsername();
            // 删除用户缓存记录
            tokenService.delLoginUser(loginUserDTO.getToken());
            // 记录用户退出日志
        }
        ServletUtils.renderString(response, JSON.toJSONString(ResultVO.ok()));
    }
}
