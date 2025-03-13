package com.vortex.msp.Entity;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Dict extends EntityBase {

    /**
     * 字典ID
     */
    private Integer dictId;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 字典键
     */
    private Integer dictKey;

    /**
     * 字典值
     */
    private String dictValue;

    /**
     * 字典说明
     */
    private String dictInfo;

}

