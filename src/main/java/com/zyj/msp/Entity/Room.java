package com.zyj.msp.Entity;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Room extends EntityBase {

    /**
     * 科室ID
     */
    private Integer roomId;

    /**
     * 科室中文名
     */
    private String roomZhName;

    /**
     * 科室英文名
     */
    private String roomEnName;

    /**
     * 科室主任
     */
    private String roomMaster;

    /**
     * 科室详细信息
     */
    private String roomInfo;
}