package com.zyj.msp.Entity;

import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Anamnesis extends EntityBase {

    /**
     * 病历ID
     */
    private Integer anamnesisId;

    /**
     * 病历所属患者
     */
    private Integer anamnesisPatient;

    /**
     * 病历所属科室
     */
    private Integer anamnesisRoom;

    /**
     * 开具医生
     */
    private Integer anamnesisDoctor;

    /**
     * 病历创建时间
     */
    private LocalDateTime anamnesisDatetime;

    /**
     * 患者自述信息
     */
    private String anamnesisPatientSelfReport;

    /**
     * 医生诊断内容
     */
    private String anamnesisDiagnosticContent;

}

