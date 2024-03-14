package top.kuoer.base.model.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PermissionRequest {

    private int id;
    private String permissionName;
    private String describe;

}
