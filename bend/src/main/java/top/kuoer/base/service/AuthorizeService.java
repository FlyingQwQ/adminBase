package top.kuoer.base.service;

import top.kuoer.base.common.Result;
import top.kuoer.base.model.vo.PaginationRequest;
import top.kuoer.base.model.entity.Permission;
import top.kuoer.base.model.entity.Role;
import top.kuoer.base.model.vo.RoleMenu;

public interface AuthorizeService {

    /**
     * 根据用户id查询角色
     * @param userId 用户ID
     * @return 角色列表
     */
    Result findRolesByUserId(int userId);

    /**
     * 查询全部角色
     * @return 角色列表
     */
    Result findAllRoles(PaginationRequest paginationRequest);

    /**
     * 根据用户id查询权限
     * @param userId 用户ID
     * @return 权限列表
     */
    Result findPermissionsByUserId(int userId);

    /**
     * 查询全部权限
     * @return 权限列表
     */
    Result findAllPermissions(PaginationRequest paginationRequest);

    /**
     * 添加新的角色
     * @param roleName 角色名
     * @param describe 角色描述
     * @return 角色ID
     */
    Result addRole(String roleName, String describe);

    /**
     * 添加新的权限
     * @param permissionName 权限名
     * @param describe 权限描述
     * @return 权限ID
     */
    Result addPermission(String permissionName, String describe);

    /**
     * 添加权限到角色
     * @param permissionId 权限ID
     * @param roleId 角色ID
     * @return 是否成功
     */
    Result addPermissionToRole(int permissionId, int roleId);

    /**
     * 添加角色到用户
     * @param roleId 角色ID
     * @param userId 用户ID
     * @return 是否成功
     */
    Result addRoleToUser(int roleId, int userId);

    /**
     * 删除用户的角色
     * @param userId 用户ID
     * @return 是否成功
     */
    Result deleteRoleToUser(int userId);

    /**
     * 批量设置一个角色的权限，会覆盖原来的
     * @param roleId 角色ID
     * @param permissionIds 权限ID数组
     * @return 是否成功
     */
    Result setRolePermission(int roleId, int[] permissionIds);

    /**
     * 删除指定角色
     * @param roleId 角色ID
     * @return 是否成功
     */
    Result deleteRole(int roleId);

    /**
     * 删除指定权限
     * @param permissionId 权限ID
     * @return 是否成功
     */
    Result deletePermission(int permissionId);


    /**
     * 编辑权限
     * @param permission 权限信息
     * @return 是否成功
     */
    Result editPermission(Permission permission);

    /**
     * 编辑角色
     * @param role 角色信息
     * @return 是否成功
     */
    Result editRole(Role role);


    /**
     * 获取角色的菜单
     * @param roleId 角色ID
     * @return 菜单列表
     */
    Result getAllRoleMenu(int roleId);


    /**
     * 设置角色菜单
     * @param roleMenu 选择的数据
     * @return 是否成功
     */
    Result setRoleMenu(RoleMenu roleMenu);

}
