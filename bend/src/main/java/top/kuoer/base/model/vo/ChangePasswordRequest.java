package top.kuoer.base.model.vo;

import lombok.Data;

@Data
public class ChangePasswordRequest {

    private String password;
    private String confirmPassword;

}
