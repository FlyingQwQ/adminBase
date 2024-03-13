package top.kuoer.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import top.kuoer.base.model.entity.Permission;

import java.util.List;

@Mapper
@Repository
public interface PermissionsMapper extends BaseMapper<Permission> {

    @Select("SELECT permissions.* FROM role_permissions JOIN permissions ON role_permissions.permissionid = permissions.id WHERE role_permissions.roleid = #{id}")
    List<Permission> findPermissionsByRoleId(@Param("id") int roleId);

    @Insert("insert into permissions (permissionName, describe) values (#{permissionName}, #{describe})")
    boolean insertPermission(@Param("permissionName") String permissionName, @Param("describe") String describe);

    @Delete("delete from permissions where id=#{permissionId}")
    boolean deletePermission(@Param("permissionId") int permissionId);

    @Update("update permissions set permissionName=#{permissionName}, describe=#{describe} where id=#{id}")
    boolean editPermissions(@Param("id") int permissionsId,
                            @Param("permissionName") String permissionName,
                            @Param("describe") String describe);

}
