package com.vortex.msp.Aop;

import com.vortex.msp.Utils.JsonConvert;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Aspect
@Component
public class ControllerLogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public HttpServletRequest GetRequest() {
        HttpServletRequest request =
                (HttpServletRequest) Objects.requireNonNull(RequestContextHolder
                        .getRequestAttributes())
                        .resolveReference(RequestAttributes.REFERENCE_REQUEST);
        return request;
    }

    // 定义切点，匹配所有 Controller 层的公共方法
//    @Around("execution(* com.vortex.msp.Controller..*(..))")
    public Object logController(ProceedingJoinPoint joinPoint) throws Throwable {
//        ObjectMapper objectMapper = new ObjectMapper();
        HttpServletRequest request = GetRequest();
        // 获取请求的 URI 和 HTTP 方法
        String uri = request.getRequestURI();
        logger.info("请求地址==>{}", uri);
        String method = request.getMethod();
        logger.info("请求方式==>{}", method);

        // 获取方法签名和方法参数
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method methodSignature = signature.getMethod();
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        String params = "";
        args.forEach(arg -> {
            logger.info("参数==>{}", arg);
        });
        if (args.size() == 0) {
            params = "无参数";
        } else {
            // 序列化参数
            params = JsonConvert.serializeObject(args);
        }

        logger.info("请求方法==>{}，请求参数==>{}", methodSignature.getName(), params);

        // 执行目标方法
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            // 异常日志记录
            logger.error("{}方法执行出现异常", methodSignature.getName(), throwable);
            throw throwable;
        }

        // 序列化返回结果
        String response = JsonConvert.serializeObject(result);

        logger.info("请求方法==>{}，响应参数==>{}", methodSignature.getName(), response);
        return result;
    }

    @Around("execution(* com.vortex.msp.Controller..*(..))")
    public Object logController1(ProceedingJoinPoint joinPoint) {
        HttpServletRequest request = GetRequest();
        logger.info("请求日志开始打印");
        logger.info("请求地址:{}", Optional.of(request.getRequestURI()).orElse(""));
        logger.info("请求方式:{}", request.getMethod());
        logger.info("请求类方法:{}", joinPoint.getSignature());
        logger.info("请求类方法参数:{}", JsonConvert.serializeObject(filterArgs(joinPoint.getArgs())));

        Object result = null;
        try {
            long start = System.currentTimeMillis();
            result = joinPoint.proceed();
            long end = System.currentTimeMillis();
            logger.info("执行耗时:{}", end - start);
            if (result != null) {
                // 序列化返回结果
                String response = JsonConvert.serializeObject(result);
                logger.info("请求响应参数:{}", response);
            }
        } catch (Throwable throwable) {
            // 异常日志记录
            logger.error("{}方法执行出现异常", joinPoint.getSignature(), throwable);
        }
        return result;
    }


    private List<Object> filterArgs(Object[] objects) {
        return Arrays.stream(objects).filter(obj -> !(obj instanceof MultipartFile)
                && !(obj instanceof HttpServletResponse)
                && !(obj instanceof HttpServletRequest)).collect(Collectors.toList());
    }


}
