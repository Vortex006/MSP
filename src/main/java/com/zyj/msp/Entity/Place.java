package com.zyj.msp.Entity;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Place extends EntityBase {

    /**
     * 地点ID，标识一个唯一的地点
     */
    private Integer placeId;

    /**
     * 行政区域代码，用于区分不同的行政区域
     */
    private String placeCode;

    /**
     * 地区名称，显示给用户的地方名字
     */
    private String placeName;

    /**
     * 省份名称，地点所在的省级行政区划
     */
    private String province;

    /**
     * 城市名称，地点所在的市级行政区划
     */
    private String city;

    /**
     * 区县名称，地点所在的县级行政区划
     */
    private String area;

}