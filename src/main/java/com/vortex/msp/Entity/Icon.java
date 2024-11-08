package com.vortex.msp.Entity;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Icon extends EntityBase {

    /**
     * 图标ID
     */
    private Integer iconId;

    /**
     * 图标名称
     */
    private String iconName;

    /**
     * 图标内容
     */
    private String iconContent;
}