package com.zyj.msp.Entity;

import lombok.*;

/**
 * 用户实体类
 * 表名：t_user
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User extends EntityBase {

    /**
     * 用户编号
     */
    private Integer userId;

    /**
     * 用户名
     * 非空，唯一索引，默认为空字符串
     */
    private String userName;

    /**
     * 密码
     * 非空，默认为空字符串
     */
    private String userPassword;

    /**
     * 盐
     * 非空，默认为空字符串
     */
    private String userSalt;

    /**
     * 状态
     * false-停用，true-启用，默认为false
     */
    private Boolean userState;

    /**
     * 用户标识
     * 1-管理员，2-患者，3-医护人员，默认为0
     */
    private Integer userFlag;

    /**
     * 关联ID
     */
    private Integer userRelevanceId;
}

