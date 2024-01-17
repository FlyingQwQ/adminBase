package top.kuoer.base.satoken;

import cn.dev33.satoken.stp.StpInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.kuoer.base.entity.Role;
import top.kuoer.base.entity.Permission;
import top.kuoer.base.utils.AuthorizeTools;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class StpInterfaceImpl implements StpInterface {

    private final AuthorizeTools authorizeTools;

    @Autowired
    public StpInterfaceImpl(AuthorizeTools authorizeTools) {
        this.authorizeTools = authorizeTools;
    }

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        int userId = Integer.parseInt((String) loginId);
        List<Permission> permissions = this.authorizeTools.findPermissionsByUserId(userId);
        Set<String> permissionNames = new HashSet<>();
        for(Permission permission : permissions) {
            permissionNames.add(permission.getPermissionName());
        }
        return permissionNames.stream().toList();
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        int userId = Integer.parseInt((String) loginId);
        List<Role> roles = this.authorizeTools.findRolesByUserId(userId);
        List<String> roleNames = new ArrayList<>();
        for(Role role : roles) {
            roleNames.add(role.getRoleName());
        }
        return roleNames;
    }

}
