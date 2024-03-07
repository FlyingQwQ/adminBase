package top.kuoer.base.service;

import org.springframework.web.bind.annotation.RequestBody;
import top.kuoer.base.common.Result;
import top.kuoer.base.model.vo.ChangePasswordRequest;
import top.kuoer.base.model.vo.PaginationRequest;
import top.kuoer.base.model.vo.UserRequest;

public interface UserService {

    /**
     * 登陆
     * @return  用户令牌
     */
    Result login(String userName, String password);

    /**
     *  注册账号
     * @param userName  用户名
     * @param password  密码
     * @param confirmPassword   确认密码
     * @return  注册结果
     */
    Result register(String userName, String password, String confirmPassword);

    /**
     * 注销登录
     * @return 是否成功
     */
    Result logout();

    /**
     * 获取全部用户信息
     * @return 用户列表
     */
    Result getAllUserInfo(PaginationRequest paginationRequest);

    /**
     * 获取用户信息
     * @param userId 用户ID
     * @return 用户信息
     */
    Result getUserInfo(int userId);

    /**
     * 删除指定用户
     * @param userId 用户ID
     * @return 是否成功
     */
    Result delete(int userId);

    /**
     * 编辑账号
     * @param userRequest 账号信息
     * @return 是否成功
     */
    Result editUser(UserRequest userRequest);

    /**
     * 用户修改账号密码
     * @param changePasswordRequest 修改密码
     * @return 是否成功
     */
    Result changePassword(ChangePasswordRequest changePasswordRequest);

}
