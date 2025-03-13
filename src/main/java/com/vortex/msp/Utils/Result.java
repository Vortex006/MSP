package com.vortex.msp.Utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vortex.msp.Enum.Code;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "响应实体类")
public class Result {

    /**
     * 状态码
     */
    @Schema(description = "状态码")
    private int code;

    /**
     * 数据
     */
    @Schema(description = "响应数据")
    private Object data;

    /**
     * 提示信息
     */
    @Schema(description = "提示信息")
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


    public static Result SUCCEED(String message, Object data) {
        return new Result(Code.SUCCEED.getCode(), message, data);
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

    public static Result Exception(Exception ex) {
        return new Result(Code.Exception.getCode(), ex.getMessage());
    }

}
