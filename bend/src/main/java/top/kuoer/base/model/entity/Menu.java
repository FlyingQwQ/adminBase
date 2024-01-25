package top.kuoer.base.model.entity;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class Menu {

    private int id;
    private String name;
    private String url;
    private int parentId;
    private String icon;
    private int sort;
    private List<Menu> children;
    private List<ResourceButtonEntity> resourceButtonEntities;
    private boolean roleHave;

}
