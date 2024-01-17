package top.kuoer.base.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DictionaryItemEntity {

    private int id;
    private String key;
    private String value;
    private String describe;
    private int dictionaryId;

}