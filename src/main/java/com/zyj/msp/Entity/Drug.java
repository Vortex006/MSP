package com.zyj.msp.Entity;

import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Drug extends EntityBase {

    /**
     * 药品ID
     */
    private Integer drugId;

    /**
     * 药品中文名
     */
    private String drugZhName;

    /**
     * 药品英文名
     */
    private String drugEnName;

    /**
     * 药品单价
     */
    private BigDecimal drugPrice;

    /**
     * 药品数量
     */
    private Integer drugNum;

    /**
     * 药品规格，默认为'个'
     */
    private String drugSpec;

    /**
     * 药品总价
     */
    private BigDecimal drugTotalPrice;

}

