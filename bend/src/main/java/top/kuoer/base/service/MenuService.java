package top.kuoer.base.service;

import top.kuoer.base.common.Result;

public interface MenuService {

    /**
     *  获取当前用户角色的菜单
     * @return 菜单列表
     */
    Result getUserMenus();

    /**
     * 获取所有的菜单
     * @return 菜单列表
     */
    Result getAllMenu();

    /**
     * 修改菜单
     * @param name 菜单名称
     * @param url 菜单URL
     * @param parentId 上级菜单ID
     * @param icon 图标
     * @param sort 排序
     * @return 是否成功
     */
    Result modifyMenu(int menuId, String name, String url, int parentId, String icon, int sort);

    /**
     * 新增菜单
     * @param name 菜单名称
     * @param url 菜单URL
     * @param parentId 上级菜单ID
     * @return 是否成功
     */
    Result addMenu(String name, String url, int parentId);

    /**
     * 删除菜单
     * @param menuId 菜单ID
     * @return 是否成功
     */
    Result deleteMenu(int menuId);

}
