package top.kuoer.base.model.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RoleRequest {

    private String roleName;
    private String describe;

}
