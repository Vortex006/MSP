package com.vortex.msp.Controller;

import com.vortex.msp.Entity.Icon;
import com.vortex.msp.Entity.Menu;
import com.vortex.msp.Service.MenuService;
import com.vortex.msp.Utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public Result listMenus() {
        List<Menu> parentMenus = menuService.listParentMenus();
        List<Menu> allMenus = new ArrayList<Menu>();

        for (Menu menu : parentMenus) {
            if (menu.getMenuHaveChild()) {
                menu.setMenuChild(menuService.listMenuByParentId(menu.getMenuId()));
            }
            allMenus.add(menu);
        }
        return Result.SUCCEED(allMenus);
    }

    @GetMapping("/icon/name/{iconName}")
    public Result getIconByName(@PathVariable("iconName") String iconName) {
        Icon icon = menuService.getIconByName(iconName);
        return Result.SUCCEED(icon);
    }

    @GetMapping("/icon/id/{iconId}")
    public Result getIconById(@PathVariable("iconId") Integer iconId) {
        Icon icon = menuService.getIcon(iconId);
        return Result.SUCCEED(icon);
    }

}
