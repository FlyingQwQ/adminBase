package top.kuoer.base.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.kuoer.base.entity.PaginationRequest;
import top.kuoer.base.service.UserService;
import top.kuoer.base.common.Result;

@RestController
@RequestMapping(path = "/user", method = {RequestMethod.POST})
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/login")
    public Result login(@RequestParam String username,
                        @RequestParam String password) {
        return this.userService.login(username, password);
    }

    @RequestMapping(path = "/register")
    public Result register(@RequestParam String userName,
                           @RequestParam String password,
                           @RequestParam String confirmPassword) {
        return this.userService.register(userName, password, confirmPassword);
    }

    @RequestMapping(path = "/logout")
    public Result logout() {
        return this.userService.logout();
    }

    @RequestMapping(path = "/getAllUserInfo")
    @SaCheckPermission("user.getAllUserInfo")
    public Result getAllUserInfo(PaginationRequest paginationRequest) {
        return this.userService.getAllUserInfo(paginationRequest);
    }

    @RequestMapping(path = "/deleteUser")
    @SaCheckPermission("user.deleteUser")
    public Result delete(@RequestParam int userId) {
        return this.userService.delete(userId);
    }



}
