package com.vortex.msp.Service;

import com.vortex.msp.Entity.Icon;
import com.vortex.msp.Entity.Menu;
import com.vortex.msp.Entity.VO.MenuVo;

import java.util.List;

public interface MenuService {

    List<Menu> listParentMenus();

    List<Menu> listMenuByParentId(Integer menuParentId);

    Icon getIcon(Integer iconId);

    Icon getIconByName(String iconName);

    MenuVo DoToVo(Menu menu);
}
