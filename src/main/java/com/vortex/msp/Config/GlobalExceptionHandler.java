package com.vortex.msp.Config;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.vortex.msp.Exception.ParameterNullException;
import com.vortex.msp.Exception.TokenNullException;
import com.vortex.msp.Utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 自定义异常 当调用方法的参数为空或不符合要求时 由此捕获
     * 注意 该异常是自定义异常，需要手动校验参数是否合法并手动抛出异常
     *
     * @param ex 异常参数
     * @return 提示参数为空
     */
    @ExceptionHandler(value = {ParameterNullException.class})
    protected Result ParameterNullExceptionHandler(ParameterNullException ex) {
        return Result.Exception(ex.getMessage());
    }

    /**
     * 除了Token为空 Token过期外，其他关于Token的异常全部由此捕获
     *
     * @param ex 异常参数
     * @return 提示Token异常
     */
    @ExceptionHandler(value = {SignatureVerificationException.class, JWTDecodeException.class})
    protected Result TokenSignatureVerificationExceptionHandler(Exception ex) {
        return Result.Exception("Token异常");
    }

    /**
     * 如果Token过期，由此捕获
     *
     * @param ex 异常参数
     * @return 提示Token过期
     */
    @ExceptionHandler(value = {TokenExpiredException.class})
    protected Result TokenExpiredExceptionHandler(TokenExpiredException ex) {
        return Result.Exception("Token过期");
    }

    /**
     * 如果出现没有被指定捕获的异常 全部由该方法捕获（兜底）
     *
     * @param ex 异常参数
     * @return 提示程序出现异常 需要联系管理员处理
     */
    @ExceptionHandler(value = {Exception.class})
    protected Result AllExceptionHandler(Exception ex) {
        System.out.println(ex.getMessage());
        return Result.Exception("程序出现异常 请联系管理员处理");
    }

    @ExceptionHandler(value = {NumberFormatException.class})
    protected Result IllegalArgumentExceptionHandler(NumberFormatException ex) {
        return Result.Exception("无效参数");
    }

    /**
     * 当Token为空时 由此捕获
     *
     * @param ex 异常参数
     * @return 提示Token为空
     */
    @ExceptionHandler(value = {TokenNullException.class})
    protected Result TokenNullExceptionHandler(TokenNullException ex) {
        return Result.Exception("Token为空");
    }

    /**
     * 使用了@Valid注解校验请求参数 当请求参数不合法时 由此捕获
     *
     * @param ex 异常参数
     * @return 返回详细的异常信息, 包括=>实体类名,字段名,提示信息
     * 返回格式为=>[实体类名=>(实体类名),字段名=>(字段名),提示信息=>(提示信息)]
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected Result MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        if (!ex.hasFieldErrors()) {
            return Result.Exception("请求参数异常");
        }
        List<FieldError> fieldErrors = ex.getFieldErrors();
        StringBuilder errorBuilder = new StringBuilder("校验参数失败：[");
        for (FieldError e : fieldErrors) {
            String str = String.format("\n{实体类名=>%s,字段名=>%s,提示信息=>%s},", e.getObjectName(), e.getField(),
                    e.getDefaultMessage());
            errorBuilder.append(str);
        }
        errorBuilder.deleteCharAt(errorBuilder.length() - 1);
        errorBuilder.append("]");
        String errorMessage = errorBuilder.toString();
        logger.error(errorMessage);
        return Result.Exception(errorMessage);
    }

}