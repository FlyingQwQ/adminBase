package top.kuoer.base.model.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ChangePasswordEntity {

    private int id;
    private String password;
    private String confirmPassword;

}
