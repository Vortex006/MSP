package com.vortex.msp.ServiceImpl;

import com.vortex.msp.Entity.Icon;
import com.vortex.msp.Entity.Menu;
import com.vortex.msp.Entity.VO.MenuVo;
import com.vortex.msp.Mapper.MenuMapper;
import com.vortex.msp.Service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuMapper menuMapper;

    @Autowired
    public MenuServiceImpl(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    @Override
    public List<Menu> listParentMenus() {
        List<Menu> parentMenus = menuMapper.listParentMenus();
        System.out.println(parentMenus);
        return parentMenus;
    }

    @Override
    public List<Menu> listMenuByParentId(Integer menuParentId) {
        List<Menu> childMenus = menuMapper.listMenuByParentId(menuParentId);
        return childMenus;
    }

    @Override
    public Icon getIcon(Integer iconId) {
        Icon icon = menuMapper.getIcon(iconId);
        return icon;
    }

    @Override
    public Icon getIconByName(String iconName) {
        Icon icon = menuMapper.getIconByName(iconName);
        return icon;
    }

    @Override
    public MenuVo DoToVo(Menu menu) {
        if (ObjectUtils.isEmpty(menu)) {
            return null;
        }
        MenuVo menuVo = MenuVo.builder()
                .menuId(menu.getMenuId())
                .menuName(menu.getMenuName())
                .menuIcon(menu.getMenuIcon())
                .menuPath(menu.getMenuPath())
                .menuHaveChild(menu.getMenuHaveChild())
                .menuChild(menu.getMenuChild())
                .menuParent(menu.getMenuParent())
                .build();
        return menuVo;
    }

}
