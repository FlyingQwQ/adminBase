package top.kuoer.base.model.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Permission {

    private int id;
    private String permissionName;
    private String describe;

}