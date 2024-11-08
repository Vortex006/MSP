package com.vortex.msp.Entity;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Deptment extends EntityBase {

    /**
     * 科室ID
     */
    private Integer deptmentId;

    /**
     * 科室名
     */
    private String deptmentName;

    /**
     * 科室类别
     */
    private Integer deptmentType;

    /**
     * 上级科室
     */
    private Integer deptmentParent;

    /**
     * 科室详细信息
     */
    private String deptmentInfo;

    /**
     * 科室地址
     */
    private String deptmentPlace;
}