package top.kuoer.base.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName("user")
public class UserInfo {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private Integer state;

}
