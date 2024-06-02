package com.zyj.msp.Service;

import com.zyj.msp.Entity.Icon;
import com.zyj.msp.Entity.Menu;

import java.util.List;

public interface MenuService {

    List<Menu> listParentMenus();

    List<Menu> listMenuByParentId(Integer menuParentId);

    Icon getIcon(Integer iconId);

    Icon getIconByName(String iconName);
}
