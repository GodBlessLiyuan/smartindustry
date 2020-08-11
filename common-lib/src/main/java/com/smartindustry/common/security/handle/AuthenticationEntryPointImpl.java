package com.smartindustry.common.security.handle;

import com.alibaba.fastjson.JSON;
import com.smartindustry.common.constant.SecurityConstant;
import com.smartindustry.common.util.ServletUtil;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * 认证失败处理类 返回未授权
 * 
 * @author ruoyi
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable
{
    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException
    {
        int code = SecurityConstant.UNAUTHORIZED;
        ServletUtil.renderString(response, JSON.toJSONString(new ResultVO(code)));
    }
}
