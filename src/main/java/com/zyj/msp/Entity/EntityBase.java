package com.zyj.msp.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EntityBase {

    /**
     * 创建人，允许为空
     */
    protected String createUser;

    /**
     * 创建时间，默认为当前时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected LocalDateTime createTime;

    /**
     * 更新人，允许为空
     */
    protected String updateUser;

    /**
     * 更新时间，默认为当前时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected LocalDateTime updateTime;

    /**
     * 扩展字段
     */
    protected Object ext;
}
