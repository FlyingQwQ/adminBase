package top.kuoer.base.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import top.kuoer.base.annotation.NotFilter;

@Data
@ToString
@TableName("dictionary")
public class DictionaryEntity {

    @TableId(type = IdType.AUTO)
    @NotFilter
    private Integer id;
    private String name;
    private String code;
    private String describe;

}
