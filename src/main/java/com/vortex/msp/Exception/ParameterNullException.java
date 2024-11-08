package com.vortex.msp.Exception;

public class ParameterNullException extends RuntimeException {

    public ParameterNullException(String message) {
        super(message);
    }

    public ParameterNullException() {
        super("参数异常");
    }

}
