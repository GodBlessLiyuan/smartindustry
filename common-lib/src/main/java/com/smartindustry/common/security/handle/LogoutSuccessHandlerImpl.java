package com.smartindustry.common.security.handle;

import com.alibaba.fastjson.JSON;
import com.smartindustry.common.bo.am.LoginUserBO;
import com.smartindustry.common.security.service.TokenService;
import com.smartindustry.common.util.ServletUtil;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义退出处理类 返回成功
 *
 * @author ruoyi
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    @Autowired
    private TokenService tokenService;

    /**
     * 退出处理
     *
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        LoginUserBO loginUserBO = tokenService.getLoginUser(request);
        if (null != loginUserBO) {
            // 删除用户缓存记录
            tokenService.delLoginUser(loginUserBO.getToken());
            // 记录用户退出日志
        }

        ServletUtil.renderString(response, JSON.toJSONString(ResultVO.ok()));
    }

}
