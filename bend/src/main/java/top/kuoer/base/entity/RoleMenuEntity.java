package top.kuoer.base.entity;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class RoleMenuEntity {

    private int roleId;
    private List<Integer> menus;
    private List<RoleResourceButtonEntity> resourceButtons;

}


