package com.vortex.msp.Mapper;

import com.vortex.msp.Entity.Icon;
import com.vortex.msp.Entity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper {

    /**
     * 查询所有菜单
     */
    List<Menu> listParentMenus();

    /**
     * 根据父菜单id查询菜单
     *
     * @param menuParentId 父菜单ID
     */
    List<Menu> listMenuByParentId(Integer menuParentId);

    Icon getIcon(Integer iconId);

    Icon getIconByName(String iconName);

}
