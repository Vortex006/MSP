package com.vortex.msp.Interceptor;

import com.vortex.msp.Config.MethodOverridingRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CodeInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String url = request.getRequestURI();
        logger.info("url=>" + url);

        if (url.equals("/v1.0")) {
            try {
                request = new MethodOverridingRequestWrapper(request, "POST");
                request.getRequestDispatcher("/test").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return true;
        }
        return false;


//        if (request.getRequestURI().equals("/")) {
//            return true;
//        }
//        // 获取请求头中的令牌
//        String token = request.getHeader("MspToken");
//        if (!StringUtils.hasText(token)) {
//            throw new TokenNullException();
//        }
//
//
//        //验证令牌
//        TokenUtil.verifyToken(token);
//        int userId = TokenUtil.decodeToken(token).get("userId").asInt();
//        String key = "userId-" + userId + "-token";
//        if (redisService.equals(key, token)) {
//            return true;
//        } else {
//            throw new TokenExpiredException("token过期");
//        }
    }


}
