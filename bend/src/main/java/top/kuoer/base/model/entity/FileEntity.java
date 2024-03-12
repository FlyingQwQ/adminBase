package top.kuoer.base.model.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FileEntity {

    private int id;
    private String fileName;
    private String tmpName;
    private int userId;
    private long createTime;
    private String flag;

}
