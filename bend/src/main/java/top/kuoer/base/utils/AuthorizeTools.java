package top.kuoer.base.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.kuoer.base.mapper.PermissionsMapper;
import top.kuoer.base.mapper.RolesMapper;
import top.kuoer.base.model.entity.Role;
import top.kuoer.base.mapper.AuthorizeMapper;
import top.kuoer.base.model.entity.Permission;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorizeTools {

    private final AuthorizeMapper authorizeMapper;
    private final PermissionsMapper permissionsMapper;
    private final RolesMapper rolesMapper;

    @Autowired
    public AuthorizeTools(AuthorizeMapper authorizeMapper, PermissionsMapper permissionsMapper, RolesMapper rolesMapper) {
        this.authorizeMapper = authorizeMapper;
        this.permissionsMapper = permissionsMapper;
        this.rolesMapper = rolesMapper;
    }

    public List<Role> findRolesByUserId(int userId) {
        return this.rolesMapper.findRolesByUserId(userId);
    }

    public List<Permission> findPermissionsByUserId(int userId) {
        List<Role> roles = this.rolesMapper.findRolesByUserId(userId);
        List<Permission> returnPermissions = new ArrayList<>();
        for(Role role : roles) {
            List<Permission> permissions = this.permissionsMapper.findPermissionsByRoleId(role.getId());
            returnPermissions.addAll(permissions);
        }

        // 去重
        returnPermissions = new ArrayList<>(returnPermissions.stream()
                .collect(Collectors.toMap(Permission::getPermissionName, p -> p, (existing, replacement) -> existing))
                .values());
        return returnPermissions;
    }

}
