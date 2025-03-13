package com.vortex.msp.Controller;

import com.vortex.msp.Entity.Icon;
import com.vortex.msp.Entity.Menu;
import com.vortex.msp.Entity.VO.MenuVo;
import com.vortex.msp.Service.MenuService;
import com.vortex.msp.Service.RedisService;
import com.vortex.msp.Utils.JsonConvert;
import com.vortex.msp.Utils.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/menu")
@Tag(name = "菜单模块")
public class MenuController {

    private final MenuService menuService;
    private final RedisService redisService;

    @Autowired
    public MenuController(MenuService menuService, RedisService redisService) {
        this.menuService = menuService;
        this.redisService = redisService;
    }

    @GetMapping
    public Result listMenus() {
        String key = "allMenus";
        if (redisService.hasKey(key)) {
            String allMenusJson = (String) redisService.getString(key);
            ArrayList<Menu> menus = JsonConvert.deserializeList(allMenusJson, Menu.class);
            return Result.SUCCEED(menus);
        } else {
            List<Menu> parentMenus = menuService.listParentMenus();
            List<Menu> allMenus = parentMenus.stream().peek(menu -> {
                if (menu.getMenuHaveChild()) {
                    menu.setMenuChild(menuService.listMenuByParentId(menu.getMenuId()));
                }
            }).collect(Collectors.toList());
            List<MenuVo> menuVoList = allMenus.stream().map(menuService::DoToVo).collect(Collectors.toList());
            String result = JsonConvert.serializeList(menuVoList);
            redisService.setString(key, result, 7, TimeUnit.DAYS);
            return Result.SUCCEED(allMenus);
        }
    }

    @GetMapping("/icon/name/{iconName}")
    public Result getIconByName(@PathVariable("iconName") String iconName) {
        if (redisService.hasKey(iconName)) {
            String iconJson = redisService.getString(iconName).toString();
            Icon icon = JsonConvert.deserializeObject(iconJson, Icon.class);
            return Result.SUCCEED(icon);
        } else {
            Icon icon = menuService.getIconByName(iconName);
            String iconJson = JsonConvert.serializeObject(icon);
            redisService.setString(iconName, iconJson, 7, TimeUnit.DAYS);
            return Result.SUCCEED(icon);
        }
    }

    @GetMapping("/icon/id/{iconId}")
    public Result getIconById(@PathVariable("iconId") Integer iconId) {
        Icon icon = menuService.getIcon(iconId);
        return Result.SUCCEED(icon);
    }

}
