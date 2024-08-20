package com.zyj.msp.Entity;

import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Message extends EntityBase {

    /**
     * 消息ID
     */
    private Integer messageId;

    /**
     * 消息标题
     */
    private String messageTitle;

    /**
     * 消息内容
     */
    private String messageContent;

    /**
     * 消息状态 - false:未读, true:已读
     */
    private Boolean messageState;

    /**
     * 发送的用户ID
     */
    private Integer messageUserId;

    /**
     * 是否为系统消息 - true:是, false:否
     */
    private Boolean messageSystem;

    /**
     * 消息发送时间
     */
    private LocalDateTime messageDatetime;
}

