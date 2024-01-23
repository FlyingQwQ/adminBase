package top.kuoer.base.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.kuoer.base.common.Result;
import top.kuoer.base.model.vo.PaginationRequest;
import top.kuoer.base.model.entity.Permission;
import top.kuoer.base.model.entity.Role;
import top.kuoer.base.model.vo.RoleMenu;
import top.kuoer.base.model.vo.RolePermissionRequest;
import top.kuoer.base.service.AuthorizeService;

@RestController
@RequestMapping(path = "/authorize", method = {RequestMethod.POST})
public class AuthorizeController {

    private final AuthorizeService authorizeService;

    @Autowired
    public AuthorizeController(AuthorizeService authorizeService) {
        this.authorizeService = authorizeService;
    }

    @RequestMapping(path = "/findRoles")
    @SaCheckPermission("authorize.findRoles")
    public Result findRolesByUserId(int userId) {
        return this.authorizeService.findRolesByUserId(userId);
    }

    @RequestMapping(path = "/findAllRoles")
    @SaCheckPermission("authorize.findAllRoles")
    public Result findAllRoles(PaginationRequest paginationRequest) {
        return this.authorizeService.findAllRoles(paginationRequest);
    }

    @RequestMapping(path = "/findPermissions")
    @SaCheckPermission("authorize.findPermissions")
    public Result findPermissionsByUserId(@RequestParam int userId) {
        return this.authorizeService.findPermissionsByUserId(userId);
    }

    @RequestMapping(path = "/findPermissionsByRoleId")
    @SaCheckPermission("authorize.findPermissionsByRoleId")
    public Result findPermissionsByRoleId(@RequestParam int roleId) {
        return this.authorizeService.findPermissionsByRoleId(roleId);
    }


    @RequestMapping(path = "/findAllPermissions")
    @SaCheckPermission("authorize.findAllPermissions")
    public Result findAllPermissions(PaginationRequest paginationRequest) {
        return this.authorizeService.findAllPermissions(paginationRequest);
    }



    @RequestMapping(path = "/addRole")
    @SaCheckPermission("authorize.addRole")
    public Result addRole(String roleName, String describe) {
        return this.authorizeService.addRole(roleName, describe);
    }

    @RequestMapping(path = "/addPermission")
    @SaCheckPermission("authorize.addPermission")
    public Result addPermission(String permissionName, String describe) {
        return this.authorizeService.addPermission(permissionName, describe);
    }

    @RequestMapping(path = "/addPermissionToRole")
    @SaCheckPermission("authorize.addPermissionToRole")
    public Result addPermissionToRole(int permissionId, int roleId) {
        return this.authorizeService.addPermissionToRole(permissionId, roleId);
    }

    @RequestMapping(path = "/addRoleToUser")
    @SaCheckPermission("authorize.addRoleToUser")
    public Result addRoleToUser(int roleId, int userId) {
        return this.authorizeService.addRoleToUser(roleId, userId);
    }

    @RequestMapping(path = "/deleteRoleToUser")
    @SaCheckPermission("authorize.deleteRoleToUser")
    public Result deleteRoleToUser(int userId) {
        return this.authorizeService.deleteRoleToUser(userId);
    }

    @RequestMapping(path = "/setRolePermission")
    @SaCheckPermission("authorize.setRolePermission")
    public Result setRolePermission(@RequestBody RolePermissionRequest rolePermission) {
        return this.authorizeService.setRolePermission(rolePermission);
    }

    @RequestMapping(path = "/deleteRole")
    @SaCheckPermission("authorize.deleteRole")
    public Result deleteRole(int roleId) {
        return this.authorizeService.deleteRole(roleId);
    }

    @RequestMapping(path = "/deletePermission")
    @SaCheckPermission("authorize.deletePermission")
    public Result deletePermission(int permissionId) {
        return this.authorizeService.deletePermission(permissionId);
    }

    @RequestMapping(path = "/editPermission")
    @SaCheckPermission("authorize.editPermission")
    public Result editPermission(Permission permission) {
        return this.authorizeService.editPermission(permission);
    }

    @RequestMapping(path = "/editRole")
    @SaCheckPermission("authorize.editRole")
    public Result editRole(Role role) {
        return this.authorizeService.editRole(role);
    }






    @RequestMapping(path = "/getAllRoleMenu")
    @SaCheckPermission("authorize.getAllRoleMenu")
    public Result getAllRoleMenu(@RequestParam int roleId) {
        return this.authorizeService.getAllRoleMenu(roleId);
    }

    @RequestMapping(path = "/setRoleMenu")
    @SaCheckPermission("authorize.setRoleMenu")
    public Result setRoleMenu(@RequestBody RoleMenu roleMenu) {
        return this.authorizeService.setRoleMenu(roleMenu);
    }




}
