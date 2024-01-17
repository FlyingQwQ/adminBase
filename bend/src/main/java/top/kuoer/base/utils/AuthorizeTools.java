package top.kuoer.base.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.kuoer.base.entity.Role;
import top.kuoer.base.mapper.AuthorizeMapper;
import top.kuoer.base.entity.Permission;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorizeTools {

    private final AuthorizeMapper authorizeMapper;

    @Autowired
    public AuthorizeTools(AuthorizeMapper authorizeMapper) {
        this.authorizeMapper = authorizeMapper;
    }

    public List<Role> findRolesByUserId(int userId) {
        return this.authorizeMapper.findRolesByUserId(userId);
    }

    public List<Permission> findPermissionsByUserId(int userId) {
        List<Role> roles = this.authorizeMapper.findRolesByUserId(userId);
        List<Permission> returnPermissions = new ArrayList<>();
        for(Role role : roles) {
            List<Permission> permissions = this.authorizeMapper.findPermissionsByRoleId(role.getId());
            returnPermissions.addAll(permissions);
        }

        // 去重
        returnPermissions = new ArrayList<>(returnPermissions.stream()
                .collect(Collectors.toMap(Permission::getPermissionName, p -> p, (existing, replacement) -> existing))
                .values());
        return returnPermissions;
    }

}
