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
@TableName("permissions")
public class Permission {

    @TableId(type = IdType.AUTO)
    @NotFilter
    private Integer id;
    @TableField("permissionName")
    private String permissionName;
    private String describe;

}
