package top.kuoer.base.entity;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class LoginResultEntity {

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
