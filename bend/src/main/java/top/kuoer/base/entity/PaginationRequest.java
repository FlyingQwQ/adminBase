package top.kuoer.base.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PaginationRequest {

    private int pageNum;
    private int pageSize;

}
