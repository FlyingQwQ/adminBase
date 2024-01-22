package top.kuoer.base.model.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RoleMenuResourceButtonQuery {

    private int id;
    private int resourceId;
    private int menuId;
    private int roleId;

}
