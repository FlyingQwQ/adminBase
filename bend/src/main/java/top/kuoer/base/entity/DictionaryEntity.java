package top.kuoer.base.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DictionaryEntity {

    private int id;
    private String name;
    private String code;
    private String describe;

}
