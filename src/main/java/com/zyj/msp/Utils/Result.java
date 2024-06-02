package com.zyj.msp.Utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zyj.msp.Enum.Code;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {

    /**
     * 状态码
     */
    private int code;

    /**
     * 数据
     */
    private Object data;

    /**
     * 提示信息
     */
    private String message;

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result SUCCEED() {
        return new Result(Code.SUCCEED.getCode(), "业务操作成功");
    }

    public static Result SUCCEED(Object data) {
        return new Result(Code.SUCCEED.getCode(), "业务操作成功", data);
    }

    public static Result FAILED() {
        return new Result(Code.FAILED.getCode(), "业务操作失败");
    }

    public static Result FAILED(String message) {
        return new Result(Code.FAILED.getCode(), message);
    }

    public static Result Exception(String message) {
        return new Result(Code.Exception.getCode(), message);
    }

}
