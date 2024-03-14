package top.kuoer.base.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName("files")
public class FileEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String fileName;
    private String tmpName;
    private Integer userId;
    private Long createTime;
    private String flag;

}
