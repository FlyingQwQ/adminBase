package top.kuoer.base.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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

}
