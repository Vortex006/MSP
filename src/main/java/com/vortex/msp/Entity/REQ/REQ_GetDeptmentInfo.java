package com.vortex.msp.Entity.REQ;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class REQ_GetDeptmentInfo extends REQ_Base {

    /**
     * 科室编号
     */
    private String deptmentNo;

}
