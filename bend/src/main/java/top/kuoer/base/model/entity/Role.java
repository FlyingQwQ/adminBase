package top.kuoer.base.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import top.kuoer.base.annotation.NotFilter;

@Data
@ToString
@TableName("roles")
public class Role {

    @TableId(type = IdType.AUTO)
    @NotFilter
    private Integer id;
    @TableField("roleName")
    private String roleName;
    private String describe;

}
