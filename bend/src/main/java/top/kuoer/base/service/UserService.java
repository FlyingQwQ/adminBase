package top.kuoer.base.service;

import top.kuoer.base.common.Result;
import top.kuoer.base.entity.PaginationRequest;

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
     * 删除指定用户
     * @param userId 用户ID
     * @return 是否成功
     */
    Result delete(int userId);
}
