package com.zyj.msp.Aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAOP {

    Logger log = LoggerFactory.getLogger(LogAOP.class);

    @Pointcut(value = "execution(* com.zyj.msp.Service..*.*(..))")
    //其中value可以写多种表达式定义切入点，后续详解
    public void pointCut() {

    }

    /**
     * 前置通知，在切点执行之前执行的操作
     */
    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {
//        Signature signature = joinPoint.getSignature();
//        MethodSignature methodSignature = (MethodSignature) signature;
//        String[] parameterNames = methodSignature.getParameterNames();
//        Object[] args = joinPoint.getArgs();
//        Object arg = args[0];

//        if(arg instanceof EntityBase){
//            ((EntityBase) arg).setCreateTime(LocalDateTime.now());
//        }
//        Class caa = arg.getClass();
//        ((caa)arg).setRoomInfo("wchexcheuc2ecxexcwecxece3");
//        Class<?> clazz = arg.getClass();
//        try {
//            Method setRoomInfoMethod = clazz.getMethod("setRoomInfo", String.class);
//            setRoomInfoMethod.invoke(arg, "wchexcheuc2ecxexcwecxece3");
//        } catch (Exception e) {
//            // 处理异常，例如方法不存在或访问权限问题
//            e.printStackTrace();
//        }

    }
//    @After("pointCut()")
//    public void after(JoinPoint joinPoint) {
//        Signature signature = joinPoint.getSignature();
//        String url1 = Arrays.toString(joinPoint.getTarget().getClass().getAnnotation(RequestMapping.class).value());
//        Object[] args = joinPoint.getArgs();
//        MethodSignature methodSignature = (MethodSignature) signature;
//        Method method = methodSignature.getMethod();
//        String[] parameterNames = methodSignature.getParameterNames();
//        GetMapping annotation = method.getAnnotation(GetMapping.class);
//        String url2 = Arrays.toString(annotation.value());
//        String url3 = url1 + url2;
//        log.info(String.format("调用了【%s】路径", url3));
//        for (int i = 0; i < parameterNames.length; i++) {
//            log.info(String.format("参数[%s]的值是[%s]", parameterNames[i], args[i]));
//        }
//    }


}




