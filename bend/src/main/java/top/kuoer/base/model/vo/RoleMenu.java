package top.kuoer.base.model.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class RoleMenu {

    private int roleId;
    private List<Integer> menus;
    private List<RoleResourceButton> resourceButtons;

}


