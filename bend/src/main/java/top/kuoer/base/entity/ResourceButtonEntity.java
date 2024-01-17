package top.kuoer.base.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ResourceButtonEntity {

    private int id;
    private String resourceName;
    private String resourceKey;
    private boolean roleHave;

}
