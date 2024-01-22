package top.kuoer.base.model.vo;

import lombok.Data;
import lombok.ToString;
import top.kuoer.base.model.entity.Permission;
import top.kuoer.base.model.entity.Role;

import java.util.List;

@Data
@ToString
public class LoginResult {

    private String token;
    private int userId;
    private String userName;
    private Authorize authorize;

    @Data
    @ToString
    public static class Authorize {

        private List<Role> roles;
        private List<Permission> permissions;

    }

}
