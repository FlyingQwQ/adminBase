package top.kuoer.base.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import top.kuoer.base.model.entity.DictionaryItemEntity;

import java.util.List;

@Mapper
@Repository
public interface DictionaryItemMapper extends MPJBaseMapper<DictionaryItemEntity> {

    @Delete("delete from dictionary_item where id=#{id}")
    boolean deleteDictionaryItem(@Param("id") int id);

    @Delete("delete from dictionary_item where dictionaryId=#{dictionaryId}")
    boolean deleteAllDictionaryItemByDictionaryId(@Param("dictionaryId") int dictionaryId);

    @Update("update dictionary_item set key=#{dictionaryItem.key}, value=#{dictionaryItem.value}, describe=#{dictionaryItem.describe} where id=#{dictionaryItem.id}")
    boolean editDictionaryItem(@Param("dictionaryItem") DictionaryItemEntity entity);

    @Insert("insert into dictionary_item (key, value, describe, dictionaryId) values (#{dictionaryItem.key}, #{dictionaryItem.value}, #{dictionaryItem.describe}, (select id from dictionary where code = #{dictionaryItem.dictionaryCode}))")
    boolean addDictionaryItemByCode(@Param("dictionaryItem") DictionaryItemEntity entity);

}
