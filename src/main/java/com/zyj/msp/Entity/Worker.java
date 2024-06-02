package com.zyj.msp.Entity;


import lombok.*;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Worker extends EntityBase {

    /**
     * 医护人员工号，默认为0
     */
    private Integer workerId;

    /**
     * 姓名，不能为空，默认为"无"
     */
    private String workerName;

    /**
     * 性别，不能为空，默认为"空"
     */
    private String workerSex;

    /**
     * 年龄，不能为空，默认为0
     */
    private Integer workerAge;

    /**
     * 联系电话，不能为空，默认为"无"
     */
    private String workerPhone;

    /**
     * 血型，不能为空，默认为"无"
     */
    private String workerBlood;

    /**
     * 出生日期，不能为空，默认为'2024-03-25'
     */
    private Date workerBirthday;

    /**
     * 身份证号，不能为空，默认为"无"
     */
    private String workerCertNo;

    /**
     * 最高学历，不能为空，默认为"无"
     */
    private String workerMaxDegree;

    /**
     * 国籍，不能为空，默认为"中国"
     */
    private String workerNationality;

    /**
     * 民族，不能为空，默认为"汉族"
     */
    private String workerEthnicity;

    /**
     * 政治面貌，不能为空，默认为"群众"
     */
    private String workerPoliticalStatus;

    /**
     * 籍贯_省市，不能为空，默认为"无"
     */
    private String workerAncestralPlace;

    /**
     * 居住地，不能为空，默认为"无"
     */
    private String workerPlace;

    /**
     * 工作科室，不能为空，默认为0
     */
    private Integer workerWorkRoom;

    /**
     * 工作类型，不能为空，默认为0
     */
    private Integer workerWorkType;

    /**
     * 职称，不能为空，默认为"普通员工"
     */
    private String workerJobTitle;

    /**
     * 工龄_单位年，不能为空，默认为0
     */
    private Integer workerWorkAge;

    /**
     * 入职日期，不能为空，默认为'2024-03-25'
     */
    private Date workerEnrollmentDate;

    /**
     * 毕业院校，不能为空，默认为"无"
     */
    private String schoolName;

    /**
     * 专业，不能为空，默认为"无"
     */
    private String schoolProfession;

    /**
     * 毕业时间，不能为空，默认为'2024-03-25'
     */
    private Date schoolFinishDate;
}
