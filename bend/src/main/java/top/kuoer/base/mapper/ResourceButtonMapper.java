package top.kuoer.base.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import top.kuoer.base.model.entity.ResourceButtonEntity;

import java.util.List;

@Mapper
@Repository
public interface ResourceButtonMapper {

    @Select("SELECT * from resource_button")
    List<ResourceButtonEntity> findAllResourceButton();

    @Select("SELECT resource_button.* FROM menu_resource_button JOIN resource_button ON menu_resource_button.resourceId = resource_button.id WHERE menu_resource_button.menuId = #{id}")
    List<ResourceButtonEntity> findResourceButtonByMenuId(@Param("id") int menuId);


    @Insert("insert into menu_resource_button (resourceId, menuId) values (#{resourceId}, #{menuId})")
    boolean addResourceButtonByMenuId(@Param("menuId") int menuId, @Param("resourceId") int resourceId);

    @Insert("insert into resource_button (resourceName, resourceKey) values (#{resourceName}, #{resourceKey})")
    boolean addResourceButton(@Param("resourceName") String resourceName, @Param("resourceKey") String resourceKey);


    @Delete("delete from menu_resource_button where menuId=#{menuId} and resourceId=#{resourceId}")
    boolean deleteResourceButtonByMenuId(@Param("menuId") int menuId, @Param("resourceId") int resourceId);

    @Delete("delete from menu_resource_button where menuId=#{menuId}")
    boolean deleteAllResourceButtonByMenuId(@Param("menuId") int menuId);

    @Delete("delete from resource_button where id=#{resourceId}")
    boolean deleteResourceButton(@Param("resourceId") int resourceId);


    @Update("update resource_button set resourceName=#{resourceName}, resourceKey=#{resourceKey} where id=#{resourceId}")
    boolean editResourceButton(@Param("resourceId") int resourceId,
                               @Param("resourceName") String resourceName,
                               @Param("resourceKey") String resourceKey);


}
