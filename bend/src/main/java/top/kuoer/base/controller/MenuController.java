package top.kuoer.base.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.kuoer.base.common.Result;
import top.kuoer.base.entity.Menu;
import top.kuoer.base.service.MenuService;

@RestController
@RequestMapping(path = "/menu", method = {RequestMethod.POST})
public class MenuController {

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @RequestMapping(path = "/getUserMenus")
    @SaCheckLogin
    public Result getUserMenus() {
        return this.menuService.getUserMenus();
    }

    @RequestMapping(path = "/getAllMenus")
    @SaCheckPermission("menu.getAllMenus")
    public Result getAllMenus() {
        return this.menuService.getAllMenu();
    }

    @RequestMapping(path = "/modifyMenu")
    @SaCheckPermission("menu.modifyMenu")
    public Result modifyMenu(@RequestParam int menuId,
                             @RequestParam String name,
                             @RequestParam String url,
                             @RequestParam int parentId,
                             String icon) {
        return this.menuService.modifyMenu(menuId, name, url, parentId, icon);
    }

    @RequestMapping(path = "/addMenu")
    @SaCheckPermission("menu.addMenu")
    public Result addMenu(@RequestParam String name,
                          @RequestParam String url,
                          @RequestParam int parentId) {
        return this.menuService.addMenu(name, url, parentId);
    }

    @RequestMapping(path = "/deleteMenu")
    @SaCheckPermission("menu.deleteMenu")
    public Result deleteMenu(@RequestParam int menuId) {
        return this.menuService.deleteMenu(menuId);
    }



}
