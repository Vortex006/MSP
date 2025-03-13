package com.vortex.msp.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Version extends EntityBase {

    /**
     * 版本ID
     */
    private Integer versionId;

    /**
     * 版本类型
     */
    private Integer versionType;

    /**
     * 版本号
     */
    private String versionNo;

    /**
     * 版本名称
     */
    private String versionName;

    /**
     * 版本资源
     */
    private String versionResource;

    /**
     * 版本备注
     */
    private String versionRemark;

    /**
     * 是否最新版本
     */
    private Boolean latest;

}
