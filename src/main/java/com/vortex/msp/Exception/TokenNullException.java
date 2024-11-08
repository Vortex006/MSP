package com.vortex.msp.Exception;

public class TokenNullException extends RuntimeException {

    public TokenNullException(String message) {
        super(message);
    }

    public TokenNullException() {
        super();
    }
}