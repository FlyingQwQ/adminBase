package top.kuoer.base.model.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ResourceButtonRequest {

    private String resourceName;
    private String resourceKey;

}
