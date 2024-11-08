package com.vortex.msp.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LogEntity {

    /**
     * 日志id
     */
    private Integer id;

    /**
     * 时间
     */
    private String time;

    /**
     * 日志级别
     */
    private String level;

    /**
     * 线程
     */
    private String thread;

    /**
     * 日志类
     */
    private String logger;

    /**
     * 日志内容
     */
    private String message;

}
