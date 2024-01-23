package top.kuoer.base.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.kuoer.base.common.Result;
import top.kuoer.base.common.ResultCode;
import top.kuoer.base.mapper.AuthorizeMapper;
import top.kuoer.base.mapper.MenuMapper;
import top.kuoer.base.mapper.ResourceButtonMapper;
import top.kuoer.base.model.entity.*;
import top.kuoer.base.model.vo.PaginationRequest;
import top.kuoer.base.model.vo.RoleMenu;
import top.kuoer.base.model.vo.RolePermissionRequest;
import top.kuoer.base.model.vo.RoleResourceButton;
import top.kuoer.base.service.AuthorizeService;

import java.util.Arrays;
import java.util.List;

@Service
public class AuthorizeServiceImpl implements AuthorizeService {

    private final AuthorizeMapper authorizeMapper;
    private final MenuMapper menuMapper;
    private final ResourceButtonMapper resourceButtonMapper;

    @Autowired
    public AuthorizeServiceImpl(AuthorizeMapper authorizeMapper, MenuMapper menuMapper, ResourceButtonMapper resourceButtonMapper) {
        this.authorizeMapper = authorizeMapper;
        this.menuMapper = menuMapper;
        this.resourceButtonMapper = resourceButtonMapper;
    }

    @Override
    public Result findRolesByUserId(int userId) {
        return new Result(ResultCode.SUCCESS, this.authorizeMapper.findRolesByUserId(userId));
    }

    @Override
    public Result findAllRoles(PaginationRequest paginationRequest) {
        PageHelper.startPage(paginationRequest.getPageNum(), paginationRequest.getPageSize());
        List<Role> roleList = this.authorizeMapper.findAllRoles();
        if(!roleList.isEmpty()) {
            return new Result(ResultCode.SUCCESS, PageInfo.of(roleList));
        }
        return new Result(ResultCode.NOTFOUND, null);
    }

    @Override
    public Result findPermissionsByUserId(int userId) {
        return null;
    }

    @Override
    public Result findPermissionsByRoleId(int roleId) {
        return new Result(ResultCode.SUCCESS, this.authorizeMapper.findPermissionsByRoleId(roleId));
    }

    @Override
    public Result findAllPermissions(PaginationRequest paginationRequest) {
        PageHelper.startPage(paginationRequest.getPageNum(), paginationRequest.getPageSize());
        List<Permission> permissionList = this.authorizeMapper.findAllPermissions();
        if(!permissionList.isEmpty()) {
            return new Result(ResultCode.SUCCESS, PageInfo.of(permissionList));
        }
        return new Result(ResultCode.NOTFOUND, null);
    }

    @Override
    public Result addRole(String roleName, String describe) {
        if(this.authorizeMapper.insertRole(roleName, describe)) {
            return new Result(ResultCode.SUCCESS, "添加成功");
        }
        return new Result(ResultCode.OPERATIONFAIL, "添加失败");
    }

    @Override
    public Result addPermission(String permissionName, String describe) {
        if(this.authorizeMapper.insertPermission(permissionName, describe)) {
            return new Result(ResultCode.SUCCESS, "添加成功");
        }
        return new Result(ResultCode.OPERATIONFAIL, "添加失败");
    }

    @Override
    public Result addPermissionToRole(int permissionId, int roleId) {
        if(this.authorizeMapper.addPermissionToRole(permissionId, roleId)) {
            return new Result(ResultCode.SUCCESS, "添加成功");
        }
        return new Result(ResultCode.OPERATIONFAIL, "添加失败");
    }

    @Override
    public Result addRoleToUser(int roleId, int userId) {
        if(this.authorizeMapper.addRoleToUser(roleId, userId)) {
            return new Result(ResultCode.SUCCESS, "添加成功");
        }
        return new Result(ResultCode.OPERATIONFAIL, "添加失败");
    }



    @Override
    public Result deleteRoleToUser(int userId) {
        if(this.authorizeMapper.deleteUserRole(userId)) {
            return new Result(ResultCode.SUCCESS, "删除成功");
        }
        return new Result(ResultCode.OPERATIONFAIL, "删除失败");
    }

    @Override
    public Result setRolePermission(RolePermissionRequest rolePermission) {
        this.authorizeMapper.deleteRolePermissionsByRoleId(rolePermission.getRoleId());
        for (int permissionId : rolePermission.getPermissionIds()) {
            this.authorizeMapper.addPermissionToRole(permissionId, rolePermission.getRoleId());
        }
        return new Result(ResultCode.SUCCESS, "操作完成");
    }

    @Override
    public Result deleteRole(int roleId) {
        this.authorizeMapper.deleteRolePermissionsByRoleId(roleId);
        this.authorizeMapper.deleteRole(roleId);
        return new Result(ResultCode.SUCCESS, "删除成功");
    }

    @Override
    public Result deletePermission(int permissionId) {
        if(this.authorizeMapper.deletePermission(permissionId)) {
            return new Result(ResultCode.SUCCESS, "删除成功");
        }
        return new Result(ResultCode.OPERATIONFAIL, "删除失败");
    }

    @Override
    public Result editPermission(Permission permission) {
        if(this.authorizeMapper.editPermissions(permission.getId(), permission.getPermissionName(), permission.getDescribe())) {
            return new Result(ResultCode.SUCCESS, "修改成功");
        }
        return new Result(ResultCode.OPERATIONFAIL, "修改失败");
    }

    @Override
    public Result editRole(Role role) {
        if(this.authorizeMapper.editRole(role.getId(), role.getRoleName(), role.getDescribe())) {
            return new Result(ResultCode.SUCCESS, "修改成功");
        }
        return new Result(ResultCode.OPERATIONFAIL, "修改失败");
    }

    @Override
    public Result getAllRoleMenu(int roleId) {

        List<RoleMenuQuery> roleMenuQueries = this.authorizeMapper.findRoleMenuByRoleId(roleId);
        List<Menu> menuList = this.menuMapper.getAllMenu();
        for(Menu menu : menuList) {

            List<RoleMenuResourceButtonQuery> roleMenuResourceButtonQueries = this.authorizeMapper.findRoleMenuResourceButtonByRoleId(roleId);
            List<ResourceButtonEntity> resourceButtonEntities = this.resourceButtonMapper.findResourceButtonByMenuId(menu.getId());
            for(ResourceButtonEntity resourceButton : resourceButtonEntities) {
                for(RoleMenuResourceButtonQuery buttonQuery : roleMenuResourceButtonQueries) {
                    if(menu.getId() == buttonQuery.getMenuId() && resourceButton.getId() == buttonQuery.getResourceId()) {
                        resourceButton.setRoleHave(true);
                        break;
                    }
                }
            }

            menu.setResourceButtonEntities(resourceButtonEntities);
            for(RoleMenuQuery query : roleMenuQueries) {
                if(menu.getId() == query.getMenuId()) {
                    menu.setRoleHave(true);
                    break;
                }
            }
        }

        List<Menu> parentList = menuList.stream().filter(e -> e.getParentId() == 0).toList();
        for (Menu menu : parentList) {
            menu.setChildren(MenuServiceImpl.getChild(menu.getId(), menuList));
        }

        return new Result(ResultCode.SUCCESS, parentList);
    }

    @Override
    @Transactional
    public Result setRoleMenu(RoleMenu roleMenu) {
        int roleId = roleMenu.getRoleId();

        this.authorizeMapper.deleteAllRoleMenuByRoleId(roleId);
        for(int menuId : roleMenu.getMenus()) {
            this.authorizeMapper.addRoleMenu(roleId, menuId);
        }
        this.authorizeMapper.deleteAllRoleMenuResourceButtonByMenuId(roleId);
        for(RoleResourceButton resourceButton : roleMenu.getResourceButtons()) {
            this.authorizeMapper.addRoleMenuResourceButton(roleId, resourceButton.getResourceId(), resourceButton.getMenuId());
        }

        return new Result(ResultCode.SUCCESS, "编辑完成");
    }


}
