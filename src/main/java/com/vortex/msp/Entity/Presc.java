package com.vortex.msp.Entity;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Presc extends EntityBase {

    /**
     * 病历ID
     */
    private Integer prescId;

    /**
     * 处方所属患者
     */
    private Integer prescPatient;

    /**
     * 处方所属科室
     */
    private Integer prescRoom;

    /**
     * 开具处方医生
     */
    private Integer prescDoctor;

    /**
     * 处方创建时间
     */
    private LocalDateTime prescDatetime;

    /**
     * 处方药品详情，可以是JSON字符串形式存储
     */
    private String prescDrug;

    /**
     * 处方总价
     */
    private BigDecimal prescMoney;

}
