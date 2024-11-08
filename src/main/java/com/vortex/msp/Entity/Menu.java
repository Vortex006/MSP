package com.vortex.msp.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Menu extends EntityBase {

    /**
     * 菜单ID
     */
    private Integer menuId;

    /**
     * 菜单名称，不允许为空，默认值为'菜单项',
     */
    private String menuName;

    /**
     * 菜单图标
     */
    private String menuIcon;

    /**
     * 菜单路径
     */
    private String menuPath;

    /**
     * 是否有子菜单
     */
    private Boolean menuHaveChild;

    /**
     * 父菜单ID，默认值为0
     */
    private Integer menuParent;

    /**
     * 子菜单
     */
    private List<Menu> menuChild;

}
