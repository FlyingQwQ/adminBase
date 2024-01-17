package top.kuoer.base.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import top.kuoer.base.entity.UserInfo;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    @Select("SELECT id FROM user WHERE username=#{name} and password=#{password}")
    Integer findIdByUnameAndPwd(@Param("name") String username, @Param("password") String password);

    @Select("SELECT * FROM user WHERE username=#{name}")
    UserInfo findUserInfoByName(@Param("name") String username);

    @Insert("insert into user (username, password) values (#{name}, #{pwd})")
    boolean insertUser(@Param("name") String username, @Param("pwd") String password);

    @Select("SELECT id, username FROM user")
    List<UserInfo> getAllUserInfo();

    @Delete("delete from user where id=#{id}")
    boolean delete(@Param("id") int userId);

}
