package com.vortex.msp.Interceptor;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.vortex.msp.Exception.TokenNullException;
import com.vortex.msp.Service.RedisService;
import com.vortex.msp.Utils.TokenUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JWTInterceptor implements HandlerInterceptor {

    private RedisService redisService;

    @Autowired
    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response,
                             @NotNull Object handler) {
        if (request.getRequestURI().equals("/")) {
            return true;
        }
        // 获取请求头中的令牌
        String token = request.getHeader("MspToken");
        if (!StringUtils.hasText(token)) {
            throw new TokenNullException();
        }
        //验证令牌
        TokenUtil.verifyToken(token);
        String userId = TokenUtil.decodeToken(token).get("userId").asString();
        String key = "userId-" + userId + "-token";
        if (redisService.equals(key, token)) {
            request.setAttribute("userId", userId);
            return true;
        } else {
            throw new TokenExpiredException("token过期");
        }
    }
}
