package com.vortex.msp.Entity.RESP;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RESP_GetDeptmentInfo extends RESP_Base {

    /**
     * 科室编号
     */
    private String deptmentNo;

    /**
     * 科室名称
     */
    private String deptmentName;

    /**
     * 科室父级编号
     */
    private String deptmentParent;

    /**
     * 科室位置
     */
    private String deptmentPlace;

}
