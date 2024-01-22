package top.kuoer.base.model.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserInfo {

    private int id;
    private String username;
    private String password;

}
