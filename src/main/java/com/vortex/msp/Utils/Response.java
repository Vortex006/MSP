package com.vortex.msp.Utils;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class Response<T> {
    @Schema(description = "状态码")
    private int code;
    @Schema(description = "响应数据")
    private String message;
    @Schema(description = "提示信息")
    private T data;

}
