package com.vortex.msp.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Schema(description = "医生实体类")
public class Doctor extends EntityBase {

    /**
     * 医生ID
     */
    private Integer doctorId;

    /**
     * 医生姓名
     */
    private String doctorName;

    /**
     * 医生性别
     */
    private Integer doctorSex;

    /**
     * 医生年龄
     */
    private Integer doctorAge;

    /**
     * 医生所属科室ID
     */
    private Integer doctorDeptmentId;

    /**
     * 医生职称
     */
    private String doctorTitle;

    /**
     * 医生电话
     */
    private String doctorPhone;
}
