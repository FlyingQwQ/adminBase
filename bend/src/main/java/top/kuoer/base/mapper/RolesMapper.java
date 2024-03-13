package top.kuoer.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import top.kuoer.base.model.entity.Role;

import java.util.List;

@Mapper
@Repository
public interface RolesMapper extends BaseMapper<Role> {

    @Select("SELECT roles.* FROM user_roles JOIN roles ON user_roles.roleid = roles.id WHERE user_roles.userid = #{id}")
    List<Role> findRolesByUserId(@Param("id") int userId);

    @Select("SELECT * FROM roles where roleName=#{roleName}")
    Role findRoleByRoleName(@Param("roleName") int roleName);

    @Insert("insert into roles (roleName, describe) values (#{roleName}, #{describe})")
    boolean insertRole(@Param("roleName") String roleName, @Param("describe") String describe);

    @Delete("delete from roles where id=#{roleId}")
    boolean deleteRole(@Param("roleId") int roleId);

    @Update("update roles set roleName=#{roleName}, describe=#{describe} where id=#{id}")
    boolean editRole(@Param("id") int roleId,
                     @Param("roleName") String roleName,
                     @Param("describe") String describe);

}
