package top.kuoer.base.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RoleMenuQuery {

    private int id;
    private int roleId;
    private int menuId;

}
