package top.kuoer.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import top.kuoer.base.model.entity.DictionaryEntity;
import top.kuoer.base.model.entity.DictionaryItemEntity;

import java.util.List;

@Mapper
@Repository
public interface DictionaryMapper extends MPJBaseMapper<DictionaryEntity> {

    @Select("SELECT dictionary_item.* FROM dictionary JOIN dictionary_item ON dictionary.id = dictionary_item.dictionaryId WHERE dictionary.code = #{code}")
    List<DictionaryItemEntity> findDictionaryItemByCode(@Param("code") String code);

    @Insert("insert into dictionary (name, code, describe) values (#{dictionary.name}, #{dictionary.code}, #{dictionary.describe})")
    boolean addDictionary(@Param("dictionary") DictionaryEntity entity);

    @Delete("delete from dictionary where id=#{id}")
    boolean deleteDictionary(@Param("id") int id);

    @Update("update dictionary set name=#{dictionary.name}, code=#{dictionary.code}, describe=#{dictionary.describe} where id=#{dictionary.id}")
    boolean editDictionary(@Param("dictionary") DictionaryEntity entity);

}
