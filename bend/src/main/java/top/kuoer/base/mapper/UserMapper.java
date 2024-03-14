package top.kuoer.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import top.kuoer.base.model.entity.ChangePasswordEntity;
import top.kuoer.base.model.entity.UserInfo;
import top.kuoer.base.model.dto.UserRequest;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<UserInfo> {

    @Select("SELECT id FROM user WHERE username=#{name} and password=#{password}")
    Integer findIdByUnameAndPwd(@Param("name") String username, @Param("password") String password);

    @Select("SELECT * FROM user WHERE id=#{id}")
    UserInfo findUserInfoById(@Param("id") int userId);

    @Select("SELECT * FROM user WHERE username=#{name}")
    UserInfo findUserInfoByName(@Param("name") String username);

    @Insert("insert into user (username, password) values (#{name}, #{pwd})")
    boolean insertUser(@Param("name") String username, @Param("pwd") String password);

    @Select("SELECT * FROM user where id=#{id}")
    UserInfo getUserInfoById(@Param("id") int userId);

    @Delete("delete from user where id=#{id}")
    boolean delete(@Param("id") int userId);

    boolean editUser(UserRequest user); //使用mapper-xml

    @Update("update user set password=#{pwd.password} where id=#{pwd.id}")
    boolean changePassword(@Param("pwd") ChangePasswordEntity changePassword);

}
