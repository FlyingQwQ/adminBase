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
@TableName("dictionary_item")
public class DictionaryItemEntity {

    @TableId(type = IdType.AUTO)
    @NotFilter
    private Integer id;
    private String key;
    private String value;
    @TableField("describe")
    private String describe;
    @TableField("dictionaryId")
    private Integer dictionaryId;
    @TableField(exist = false)
    private String dictionaryCode;

}
