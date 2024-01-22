package top.kuoer.base.mapper;

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


    // --------查询-------------------

    @Select("SELECT roles.* FROM user_roles JOIN roles ON user_roles.roleid = roles.id WHERE user_roles.userid = #{id}")
    List<Role> findRolesByUserId(@Param("id") int userId);

    @Select("SELECT * FROM roles")
    List<Role> findAllRoles();

    @Select("SELECT * FROM roles where roleName=#{roleName}")
    Role findRoleByRoleName(@Param("roleName") int roleName);

    @Select("SELECT permissions.* FROM role_permissions JOIN permissions ON role_permissions.permissionid = permissions.id WHERE role_permissions.roleid = #{id}")
    List<Permission> findPermissionsByRoleId(@Param("id") int roleId);

    @Select("SELECT * FROM permissions")
    List<Permission> findAllPermissions();


    // --------新增-------------------

    @Insert("insert into roles (roleName, describe) values (#{roleName}, #{describe})")
    boolean insertRole(@Param("roleName") String roleName, @Param("describe") String describe);

    @Insert("insert into permissions (permissionName, describe) values (#{permissionName}, #{describe})")
    boolean insertPermission(@Param("permissionName") String permissionName, @Param("describe") String describe);

    @Insert("insert into role_permissions (roleid, permissionid) values (#{roleId}, #{permissionId})")
    boolean addPermissionToRole(@Param("permissionId") int permissionId, @Param("roleId") int roleId);

    @Insert("insert into user_roles (userid, roleid) values (#{roleId}, #{userId})")
    boolean addRoleToUser(@Param("roleId") int roleId, @Param("userId") int userId);


    // --------删除-------------------

    @Delete("delete from roles where id=#{roleId}")
    boolean deleteRole(@Param("roleId") int roleId);

    @Delete("delete from permissions where id=#{permissionId}")
    boolean deletePermission(@Param("permissionId") int permissionId);

    @Delete("delete from role_permissions where roleid=#{roleId}")
    boolean deleteRolePermissionsByRoleId(@Param("roleId") int roleId);

    @Delete("delete from user_roles where userid=#{roleId}")
    boolean deleteUserRole(int userId);

    // --------修改-------------------

    @Update("update permissions set permissionName=#{permissionName}, describe=#{describe} where id=#{id}")
    boolean editPermissions(@Param("id") int permissionsId,
                               @Param("permissionName") String permissionName,
                               @Param("describe") String describe);


    @Update("update roles set roleName=#{roleName}, describe=#{describe} where id=#{id}")
    boolean editRole(@Param("id") int roleId,
                        @Param("roleName") String roleName,
                        @Param("describe") String describe);



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
