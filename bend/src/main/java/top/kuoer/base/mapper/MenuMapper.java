package top.kuoer.base.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import top.kuoer.base.model.entity.Menu;

import java.util.List;

@Mapper
@Repository
public interface MenuMapper {

    @Select("SELECT * from menu")
    List<Menu> getAllMenu();

    @Update("update menu set name=#{name}, url=#{url}, parentId=#{parentId}, icon=#{icon}, sort=#{sort} where id=#{menuId}")
    boolean modifyMenuByMenuId(@Param("menuId") int menuId,
                               @Param("name") String name,
                               @Param("url") String url,
                               @Param("parentId") int parentId,
                               @Param("icon") String icon,
                               @Param("sort") int sort);

    @Insert("insert into menu (name, url, parentId) values (#{name}, #{url}, #{parentId})")
    boolean addMenu(@Param("name") String name,
                    @Param("url") String url,
                    @Param("parentId") int parentId);

    @Delete("delete from menu where id=#{id}")
    boolean deleteMenu(@Param("id") int menuId);

}
