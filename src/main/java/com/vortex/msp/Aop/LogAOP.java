package com.vortex.msp.Aop;

import com.vortex.msp.Entity.EntityBase;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class LogAOP {

    Logger log = LoggerFactory.getLogger(LogAOP.class);

    @Pointcut(value = "execution(* com.vortex.msp.Controller..*.*(..))")
    //其中value可以写多种表达式定义切入点，后续详解
    public void pointCut() {

    }

    @Pointcut(value = "execution(* com.vortex.msp.Service..*.*(..))")
    //其中value可以写多种表达式定义切入点，后续详解
    public void pointCut2() {

    }

    /**
     * 前置通知，在切点执行之前执行的操作
     */
    @Before("pointCut2()")
    public void before(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof EntityBase) {
                EntityBase entity = (EntityBase) arg;
                entity.setCreateTime(LocalDateTime.now());
                entity.setUpdateTime(LocalDateTime.now());
                entity.setCreateUser("create_user");
                entity.setUpdateUser("update_user");
            }
        }
    }

//    @After("pointCut()")
//    public void after(JoinPoint joinPoint) {
//        Signature signature = joinPoint.getSignature();
//        String url1 = joinPoint.getTarget().getClass().getAnnotation(RequestMapping.class).value()[0];
//        Object[] args = joinPoint.getArgs();
//        MethodSignature methodSignature = (MethodSignature) signature;
//        Method method = methodSignature.getMethod();
//        String[] parameterNames = methodSignature.getParameterNames();
//        String url2 = "";
//        String reqMethodName = "";
//        if (!ObjectUtils.isEmpty(method.getAnnotation(GetMapping.class))) {
//            GetMapping annotation = method.getAnnotation(GetMapping.class);
//            if (!ObjectUtils.isEmpty(annotation.value())) {
//                url2 = annotation.value()[0];
//            } else {
//                url2 = "";
//            }
//            reqMethodName = "GET";
//        } else if (!ObjectUtils.isEmpty(method.getAnnotation(PostMapping.class))) {
//            PostMapping annotation = method.getAnnotation(PostMapping.class);
//            if (!ObjectUtils.isEmpty(annotation.value())) {
//                url2 = annotation.value()[0];
//            } else {
//                url2 = "";
//            }
//            reqMethodName = "POST";
//        } else if (!ObjectUtils.isEmpty(method.getAnnotation(PutMapping.class))) {
//            PutMapping annotation = method.getAnnotation(PutMapping.class);
//            if (!ObjectUtils.isEmpty(annotation.value())) {
//                url2 = annotation.value()[0];
//            } else {
//                url2 = "";
//            }
//            reqMethodName = "PUT";
//        } else if (!ObjectUtils.isEmpty(method.getAnnotation(DeleteMapping.class))) {
//            DeleteMapping annotation = method.getAnnotation(DeleteMapping.class);
//            if (!ObjectUtils.isEmpty(annotation.value())) {
//                url2 = annotation.value()[0];
//            } else {
//                url2 = "";
//            }
//            reqMethodName = "DELETE";
//        }
//        String url3 = url1 + url2;
//        log.info(String.format("请求方式==> [%s], 请求路径==> [%s]", reqMethodName, url3));
//        for (int i = 0; i < parameterNames.length; i++) {
//            log.info(String.format("参数[%s]的值是[%s]", parameterNames[i], args[i]));
//        }
//    }


}




