package top.kuoer.base.model.vo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DictionaryVO {

    private int id;
    private String name;
    private String code;
    private String describe;

}
