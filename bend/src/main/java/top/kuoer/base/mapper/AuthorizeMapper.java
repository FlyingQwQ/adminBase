package top.kuoer.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import top.kuoer.base.model.entity.Role;
import top.kuoer.base.model.entity.Permission;
import top.kuoer.base.model.entity.RoleMenuQuery;
import top.kuoer.base.model.entity.RoleMenuResourceButtonQuery;

import java.util.List;

@Mapper
@Repository
public interface AuthorizeMapper {


    // --------新增-------------------

    @Insert("insert into user_roles (userid, roleid) values (#{userId}, #{roleId})")
    boolean addRoleToUser(@Param("roleId") int roleId, @Param("userId") int userId);

    @Insert("insert into role_permissions (roleid, permissionid) values (#{roleId}, #{permissionId})")
    boolean addPermissionToRole(@Param("permissionId") int permissionId, @Param("roleId") int roleId);


    // --------删除-------------------


    @Delete("delete from role_permissions where roleid=#{roleId}")
    boolean deleteRolePermissionsByRoleId(@Param("roleId") int roleId);

    @Delete("delete from user_roles where userid=#{userId}")
    boolean deleteUserRole(@Param("userId") int userId);

    // --------修改-------------------






    @Select("SELECT * FROM role_menu where roleId=#{roleId}")
    List<RoleMenuQuery> findRoleMenuByRoleId(@Param("roleId") int roleId);

    @Insert("insert into role_menu (roleId, menuId) values (#{roleId}, #{menuId})")
    boolean addRoleMenu(@Param("roleId") int roleId, @Param("menuId") int menuId);

    @Delete("delete from role_menu where roleId=#{roleId}")
    boolean deleteAllRoleMenuByRoleId(@Param("roleId") int roleId);

    @Select("SELECT * FROM role_menu_resource_button where roleId=#{roleId}")
    List<RoleMenuResourceButtonQuery> findRoleMenuResourceButtonByRoleId(@Param("roleId") int roleId);

    @Insert("insert into role_menu_resource_button (resourceId, menuId, roleId) values (#{resourceId}, #{menuId}, #{roleId})")
    boolean addRoleMenuResourceButton(@Param("roleId") int roleId, @Param("resourceId") int resourceId, @Param("menuId") int menuId);

    @Delete("delete from role_menu_resource_button where roleId=#{roleId}")
    boolean deleteAllRoleMenuResourceButtonByMenuId(@Param("roleId") int roleId);

}
