package top.kuoer.base.model.dto;

import lombok.Data;

@Data
public class ChangePasswordRequest {

    private String password;
    private String confirmPassword;

}
