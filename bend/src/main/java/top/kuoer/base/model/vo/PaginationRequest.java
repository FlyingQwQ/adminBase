package top.kuoer.base.model.vo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PaginationRequest {

    private int pageNum;
    private int pageSize;

}
