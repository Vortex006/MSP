package com.vortex.msp.Entity.VO;

import com.vortex.msp.Entity.Menu;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MenuVo {

    /**
     * 菜单ID
     */
    private Integer menuId;

    /**
     * 菜单名称
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
