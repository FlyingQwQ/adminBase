package top.kuoer.base.model.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DictionaryItemRequest {

    private String key;
    private String value;
    private String describe;

}
