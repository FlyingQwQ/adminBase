package top.kuoer.base.service;

import top.kuoer.base.common.Result;
import top.kuoer.base.entity.PaginationRequest;

public interface ResourceButtonService {

    /**
     * 查询全部资源按钮
     * @return 资源按钮列表
     */
    Result findAllResourceButton(PaginationRequest paginationRequest);

    /**
     * 通过菜单ID查询资源按钮
     * @param menuId 菜单ID
     * @return 资源按钮列表
     */
    Result findResourceButtonByMenuId(int menuId);

    /**
     * 给菜单添加资源按钮
     * @param menuId 菜单ID
     * @param resourceId 资源ID
     * @return 是否成功
     */
    Result addResourceButtonByMenuId(int menuId, int resourceId);

    /**
     * 通过菜单ID删除资源按钮
     * @param menuId 菜单ID
     * @param resourceId 资源ID
     * @return 是否成功
     */
    Result deleteResourceButtonByMenuId(int menuId, int resourceId);

    /**
     * 新增资源按钮
     * @param resourceName 资源名称
     * @param resourceKey 资源编码
     * @return 是否成功
     */
    Result addResourceButton(String resourceName, String resourceKey);

    /**
     * 删除资源按钮
     * @param resourceId 资源ID
     * @return 是否成功
     */
    Result deleteResourceButton(int resourceId);

    /**
     * 编辑资源按钮
     * @param resourceId 资源ID
     * @param resourceName 资源名
     * @param resourceKey 资源编码
     * @return 是否成功
     */
    Result editResourceButton(int resourceId, String resourceName, String resourceKey);

}
