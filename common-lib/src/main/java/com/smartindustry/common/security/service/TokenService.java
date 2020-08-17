package com.smartindustry.common.security.service;

import com.smartindustry.common.bo.am.LoginUserBO;
import com.smartindustry.common.constant.SecurityConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
@SuppressWarnings("unchecked")
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

    private static final long MILLIS_MINUTE = 60 * 1000;

    private static final long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUserBO getLoginUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = getToken(request);
        if (!StringUtils.isEmpty(token)) {
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            // 解析对应的权限以及用户信息
            String userKey = SecurityConstant.LOGIN_TOKEN_KEY + claims.get(SecurityConstant.LOGIN_USER_KEY);
            return (LoginUserBO) redisTemplate.opsForValue().get(userKey);
        }

        return null;
    }

    /**
     * 删除用户身份信息
     */
    public void delLoginUser(String token) {
        if (!StringUtils.isEmpty(token)) {
            redisTemplate.delete(SecurityConstant.LOGIN_TOKEN_KEY + token);
        }
    }

    /**
     * 创建令牌
     *
     * @param loginUserBO 用户信息
     * @return 令牌
     */
    public String createToken(LoginUserBO loginUserBO) {
        String token = UUID.randomUUID().toString();

        loginUserBO.setToken(token);
        refreshToken(loginUserBO);

        Map<String, Object> claims = new HashMap<>();
        claims.put(SecurityConstant.LOGIN_USER_KEY, token);
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    /**
     * 验证令牌有效期，相差不足20分钟，自动刷新缓存
     *
     * @param loginUserBO
     * @return 令牌
     */
    public void verifyToken(LoginUserBO loginUserBO) {
        long expireTime = loginUserBO.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN) {
            refreshToken(loginUserBO);
        }
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUserBO 登录信息
     */
    public void refreshToken(LoginUserBO loginUserBO) {
        loginUserBO.setLoginTime(System.currentTimeMillis());
        loginUserBO.setExpireTime(loginUserBO.getLoginTime() + expireTime * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        String userKey = SecurityConstant.LOGIN_TOKEN_KEY + loginUserBO.getToken();
        redisTemplate.opsForValue().set(userKey, loginUserBO, expireTime, TimeUnit.MINUTES);
    }

    /**
     * 获取请求token
     *
     * @param request
     * @return token
     */
    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(header);
        if (!StringUtils.isEmpty(token) && token.startsWith(SecurityConstant.TOKEN_PREFIX)) {
            return token.replace(SecurityConstant.TOKEN_PREFIX, "");
        }

        return null;
    }
}
