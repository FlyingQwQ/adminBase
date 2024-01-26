package top.kuoer.base.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import top.kuoer.base.model.entity.DictionaryEntity;
import top.kuoer.base.model.entity.DictionaryItemEntity;

import java.util.List;

@Mapper
@Repository
public interface DictionaryMapper {

    @Select("SELECT * FROM dictionary")
    List<DictionaryEntity> findAllDictionary();

    @Select("SELECT dictionary_item.* FROM dictionary JOIN dictionary_item ON dictionary.id = dictionary_item.dictionaryId WHERE dictionary.code = #{code}")
    List<DictionaryItemEntity> findDictionaryItemByCode(@Param("code") String code);

    @Insert("insert into dictionary (name, code, describe) values (#{dictionary.name}, #{dictionary.code}, #{dictionary.describe})")
    boolean addDictionary(@Param("dictionary") DictionaryEntity entity);

    @Delete("delete from dictionary where id=#{id}")
    boolean deleteDictionary(@Param("id") int id);

    @Update("update dictionary set name=#{dictionary.name}, code=#{dictionary.code}, describe=#{dictionary.describe} where id=#{dictionary.id}")
    boolean editDictionary(@Param("dictionary") DictionaryEntity entity);


    @Delete("delete from dictionary_item where id=#{id}")
    boolean deleteDictionaryItem(@Param("id") int id);

    @Delete("delete from dictionary_item where dictionaryId=#{dictionaryId}")
    boolean deleteAllDictionaryItemByDictionaryId(@Param("dictionaryId") int dictionaryId);

    @Update("update dictionary_item set key=#{dictionaryItem.key}, value=#{dictionaryItem.value}, describe=#{dictionaryItem.describe} where id=#{dictionaryItem.id}")
    boolean editDictionaryItem(@Param("dictionaryItem") DictionaryItemEntity entity);

    @Insert("insert into dictionary_item (key, value, describe, dictionaryId) values (#{dictionaryItem.key}, #{dictionaryItem.value}, #{dictionaryItem.describe}, (select id from dictionary where code = #{dictionaryItem.dictionaryCode}))")
    boolean addDictionaryItemByCode(@Param("dictionaryItem") DictionaryItemEntity entity);
}
