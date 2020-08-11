package com.smartindustry.common.service;

import com.smartindustry.common.bo.am.LoginUserBO;
import com.smartindustry.common.constant.SecurityConstant;
import com.smartindustry.common.util.RedisCache;
import com.smartindustry.common.util.StringUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author: jiangzhaojie
 * @date: Created in 14:40 2020/8/3
 * @version: 1.0.0
 * @description:
 */
@Service
public class TokenService {
    /**
     * 令牌自定义标识
     */
    @Value("${token.header}")
    private String header;

    /**
     * 令牌秘钥
     */
    @Value("${token.secret}")
    private String secret;

    /**
     * 令牌有效期（默认30分钟）
     */
    @Value("${token.expireTime}")
    private int expireTime;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    @Autowired
    private RedisCache redisCache;

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUserBO getLoginUser(HttpServletRequest request)
    {
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StringUtil.isNotEmpty(token))
        {
            Claims claims = parseToken(token);
            // 解析对应的权限以及用户信息
            String uuid = (String) claims.get(SecurityConstant.LOGIN_USER_KEY);
            String userKey = getTokenKey(uuid);
            LoginUserBO user = redisCache.getCacheObject(userKey);

            return user;
        }
        return null;
    }

    /**
     * 设置用户身份信息
     */
    public void setLoginUser(LoginUserBO loginUserBO)
    {
        if (StringUtil.isNotNull(loginUserBO) && StringUtil.isNotEmpty(loginUserBO.getToken()))
        {
            refreshToken(loginUserBO);
        }
    }

    /**
     * 删除用户身份信息
     */
    public void delLoginUser(String token)
    {
        if (StringUtil.isNotEmpty(token))
        {
            String userKey = getTokenKey(token);
            redisCache.deleteObject(userKey);
        }
    }

    /**
     * 创建令牌
     *
     * @param loginUserBO 用户信息
     * @return 令牌
     */
    public String createToken(LoginUserBO loginUserBO)
    {
        String token = UUID.randomUUID().toString();
        loginUserBO.setToken(token);
        refreshToken(loginUserBO);

        Map<String, Object> claims = new HashMap<>();
        claims.put(SecurityConstant.LOGIN_USER_KEY, token);
        return createToken(claims);
    }

    /**
     * 验证令牌有效期，相差不足20分钟，自动刷新缓存
     *
     * @param loginUserBO
     * @return 令牌
     */
    public void verifyToken(LoginUserBO loginUserBO)
    {
        long expireTime = loginUserBO.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN)
        {
            refreshToken(loginUserBO);
        }
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUserBO 登录信息
     */
    public void refreshToken(LoginUserBO loginUserBO)
    {
        loginUserBO.setLoginTime(System.currentTimeMillis());
        loginUserBO.setExpireTime(loginUserBO.getLoginTime() + expireTime * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        String userKey = getTokenKey(loginUserBO.getToken());
        redisCache.setCacheObject(userKey, loginUserBO, expireTime, TimeUnit.MINUTES);
    }



    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String createToken(Map<String, Object> claims)
    {
        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
        return token;
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims parseToken(String token)
    {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token)
    {
        Claims claims = parseToken(token);
        return claims.getSubject();
    }

    /**
     * 获取请求token
     *
     * @param request
     * @return token
     */
    private String getToken(HttpServletRequest request)
    {
        String token = request.getHeader(header);
        if (StringUtil.isNotEmpty(token) && token.startsWith(SecurityConstant.TOKEN_PREFIX))
        {
            token = token.replace(SecurityConstant.TOKEN_PREFIX, "");
        }
        return token;
    }

    private String getTokenKey(String uuid)
    {
        return SecurityConstant.LOGIN_TOKEN_KEY + uuid;
    }
}
