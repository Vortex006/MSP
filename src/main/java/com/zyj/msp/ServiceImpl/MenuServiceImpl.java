package com.zyj.msp.ServiceImpl;

import com.zyj.msp.Entity.Icon;
import com.zyj.msp.Entity.Menu;
import com.zyj.msp.Mapper.MenuMapper;
import com.zyj.msp.Service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
