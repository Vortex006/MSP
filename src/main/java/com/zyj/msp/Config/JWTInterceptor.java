package com.zyj.msp.Config;

import com.zyj.msp.Exception.TokenNullException;
import com.zyj.msp.Utils.TokenUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JWTInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
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
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {
    }
}
