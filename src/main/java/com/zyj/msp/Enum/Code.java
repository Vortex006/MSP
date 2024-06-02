package com.zyj.msp.Enum;

public enum Code {

    SUCCEED(200),
    FAILED(400),
    Exception(500);

    private final int code;

    Code(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
