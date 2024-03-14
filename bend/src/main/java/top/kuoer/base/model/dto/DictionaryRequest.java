package top.kuoer.base.model.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DictionaryRequest {

    private String name;
    private String code;
    private String describe;

}
