package top.kuoer.base.model.vo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RolePermissionRequest {

    int roleId;
    Integer[] permissionIds;

}
