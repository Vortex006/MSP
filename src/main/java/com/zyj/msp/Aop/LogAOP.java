package com.zyj.msp.Aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
public class LogAOP {

    Logger log = LoggerFactory.getLogger(LogAOP.class);

    @Pointcut(value = "execution(* com.zyj.msp.Controller..*.*(..))")
    //其中value可以写多种表达式定义切入点，后续详解
    public void pointCut() {

    }

    /**
     * 前置通知，在切点执行之前执行的操作
     */
//    @Before("pointCut()")
//    public void before(JoinPoint joinPoint) {
//        RequestMapping annotation = joinPoint.getTarget().getClass().getAnnotation(RequestMapping.class);
//        String url = Arrays.toString(annotation.value());
////        String name = joinPoint.getSignature().getName();
//        log.info("调用了" + url + "路径");
//    }
    @After("pointCut()")
    public void after(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        String url1 = Arrays.toString(joinPoint.getTarget().getClass().getAnnotation(RequestMapping.class).value());
        Object[] args = joinPoint.getArgs();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        String[] parameterNames = methodSignature.getParameterNames();
        GetMapping annotation = method.getAnnotation(GetMapping.class);
        String url2 = Arrays.toString(annotation.value());
        String url3 = url1 + url2;
        log.info(String.format("调用了【%s】路径", url3));
        for (int i = 0; i < parameterNames.length; i++) {
            log.info(String.format("参数[%s]的值是[%s]", parameterNames[i], args[i]));
        }
    }
}




