package top.kuoer.base.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.kuoer.base.common.Result;
import top.kuoer.base.common.ResultCode;
import top.kuoer.base.entity.Menu;
import top.kuoer.base.entity.Role;
import top.kuoer.base.entity.RoleMenuQuery;
import top.kuoer.base.mapper.AuthorizeMapper;
import top.kuoer.base.mapper.MenuMapper;
import top.kuoer.base.mapper.ResourceButtonMapper;
import top.kuoer.base.service.MenuService;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuMapper menuMapper;
    private final ResourceButtonMapper resourceButtonMapper;
    private final AuthorizeMapper authorizeMapper;

    @Autowired
    public MenuServiceImpl(MenuMapper menuMapper, ResourceButtonMapper resourceButtonMapper, AuthorizeMapper authorizeMapper) {
        this.menuMapper = menuMapper;
        this.resourceButtonMapper = resourceButtonMapper;
        this.authorizeMapper = authorizeMapper;
    }


    @Override
    public Result getUserMenus() {
        int userId = Integer.parseInt((String) StpUtil.getLoginId());
        List<Role> roles = this.authorizeMapper.findRolesByUserId(userId);

        List<Menu> allMenuList = this.menuMapper.getAllMenu();
        List<Menu> RoleMenu = new ArrayList<>();

        for(Role role : roles) {
            List<RoleMenuQuery> roleMenuQueries = this.authorizeMapper.findRoleMenuByRoleId(role.getId());
            for(RoleMenuQuery roleMenuQuery : roleMenuQueries) {
                for(Menu menu : allMenuList) {
                    if(roleMenuQuery.getMenuId() == menu.getId()) {
                        RoleMenu.add(menu);
                    }
                }
            }
        }

        //  一级菜单
        List<Menu> parentList = RoleMenu.stream().filter(e -> e.getParentId() == 0).toList();
        //  递归调用，为所有一级菜单设置子菜单
        for (Menu menu : parentList) {
            menu.setChildren(getChild(menu.getId(), RoleMenu));
        }
        return new Result(ResultCode.SUCCESS, parentList);
    }

    @Override
    public Result getAllMenu() {
        List<Menu> allMenuList = this.menuMapper.getAllMenu();

        //  一级菜单
        List<Menu> parentList = allMenuList.stream().filter(e -> e.getParentId() == 0).toList();
        //  递归调用，为所有一级菜单设置子菜单
        for (Menu menu : parentList) {
            menu.setChildren(getChild(menu.getId(), allMenuList));
        }
        return new Result(ResultCode.SUCCESS, parentList);
    }

    @Override
    public Result modifyMenu(int menuId, String name, String url, int parentId, String icon) {
        if(this.menuMapper.modifyMenuByMenuId(menuId, name, url, parentId, icon)) {
            return new Result(ResultCode.SUCCESS, "修改成功");
        }
        return new Result(ResultCode.OPERATIONFAIL, null);
    }

    @Override
    public Result addMenu(String name, String url, int parentId) {
        if(this.menuMapper.addMenu(name, url, parentId)) {
            return new Result(ResultCode.SUCCESS, "添加成功");
        }
        return new Result(ResultCode.OPERATIONFAIL, null);
    }

    @Transactional
    @Override
    public Result deleteMenu(int menuId) {
        this.resourceButtonMapper.deleteAllResourceButtonByMenuId(menuId);
        this.menuMapper.deleteMenu(menuId);
        return new Result(ResultCode.SUCCESS, "删除成功");
    }


    public static List<Menu> getChild(Integer id, List<Menu> allList) {
        //  子菜单
        List<Menu> childList = new ArrayList<>();
        for (Menu menu : allList) {
            if (menu.getParentId() == id) {
                childList.add(menu);
            }
        }

        //  为子菜单设置子菜单
        for (Menu nav : childList) {
            nav.setChildren(getChild(nav.getId(), allList));
        }

        if (childList.isEmpty()) {
            return new ArrayList<>();
        }

        return childList;
    }

}
