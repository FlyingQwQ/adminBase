package top.kuoer.base.model.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserRequest {

    private Integer id;
    private String username;
    private String password;
    private Integer state;
    private Integer[] roles;

}
