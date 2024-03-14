package top.kuoer.base.model.vo;

import lombok.Data;

@Data
public class FileVO {

    private Integer id;
    private String fileName;
    private String tmpName;
    private Integer userId;
    private String username;
    private Long createTime;

}
