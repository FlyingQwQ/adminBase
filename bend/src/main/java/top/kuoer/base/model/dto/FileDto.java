package top.kuoer.base.model.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FileDto {

    private int id;
    private String fileName;
    private String tmpName;
    private int userId;
    private long createTime;
    private String userName;
    private String flag;

}