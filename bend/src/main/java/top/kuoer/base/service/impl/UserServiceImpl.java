package top.kuoer.base.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.kuoer.base.mapper.AuthorizeMapper;
import top.kuoer.base.model.entity.ChangePasswordEntity;
import top.kuoer.base.model.dto.ChangePasswordRequest;
import top.kuoer.base.model.dto.PaginationRequest;
import top.kuoer.base.model.entity.UserInfo;
import top.kuoer.base.mapper.UserMapper;
import top.kuoer.base.model.dto.UserRequest;
import top.kuoer.base.service.UserService;
import top.kuoer.base.common.Result;
import top.kuoer.base.common.ResultCode;
import top.kuoer.base.model.vo.LoginResult;
import top.kuoer.base.utils.AuthorizeTools;
import top.kuoer.base.utils.QueryWrapperUtils;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final AuthorizeMapper authorizeMapper;
    private final AuthorizeTools authorizeTools;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, AuthorizeMapper authorizeMapper, AuthorizeTools authorizeTools) {
        this.userMapper = userMapper;
        this.authorizeMapper = authorizeMapper;
        this.authorizeTools = authorizeTools;
    }

    @Override
    public Result login(String userName, String password) {
        Integer userId = this.userMapper.findIdByUnameAndPwd(userName, password);
        if(null != userId) {

            UserInfo userInfo = this.userMapper.selectOne(new QueryWrapper<UserInfo>().eq("id", userId));
            if(userInfo.getState() == 1) {
                return new Result(ResultCode.LOGINFAIL, "登陆失败，账号状态异常{" + userInfo.getState() + "}");
            }

            StpUtil.login(userId);

            LoginResult loginResult = new LoginResult();
            LoginResult.Authorize authorize = new LoginResult.Authorize();
            authorize.setRoles(authorizeTools.findRolesByUserId(userId));
            authorize.setPermissions(authorizeTools.findPermissionsByUserId(userId));

            loginResult.setUserId(userId);
            loginResult.setUserName(userName);
            loginResult.setToken(StpUtil.getTokenInfo().getTokenValue());
            loginResult.setAuthorize(authorize);

            return new Result(ResultCode.SUCCESS, loginResult);
        }
        return new Result(ResultCode.LOGINFAIL, "登陆失败，请检查账号或密码是否有误");
    }

    @Override
    public Result register(String userName, String password, String confirmPassword) {
        if(userName.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            return new Result(ResultCode.OPERATIONFAIL, "参数不能为空");
        }
        if(!confirmPassword.equals(password)) {
            return new Result(ResultCode.OPERATIONFAIL, "两次输入的密码不一致");
        }
        UserInfo userInfo = this.userMapper.findUserInfoByName(userName);
        if(null != userInfo) {
            return new Result(ResultCode.OPERATIONFAIL, "当前注册的用户名已经存在了");
        }
        if(this.userMapper.insertUser(userName, SaSecureUtil.md5(password))) {
            return new Result(ResultCode.SUCCESS, "用户创建成功");
        }
        return new Result(ResultCode.SUCCESS, "创建失败");
    }

    @Override
    public Result logout() {
        StpUtil.logout();
        return new Result(ResultCode.SUCCESS, "注销成功");
    }

    @Override
    public Result getAllUserInfo(PaginationRequest paginationRequest, UserRequest userRequest) {
        PageHelper.startPage(paginationRequest.getPageNum(), paginationRequest.getPageSize());
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userRequest, userInfo);
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        QueryWrapperUtils.autoLikeIfNotNull(queryWrapper, userInfo);
        return new Result(ResultCode.SUCCESS, PageInfo.of(this.userMapper.selectList(queryWrapper)));
    }

    @Override
    public Result getUserInfo(int userId) {
        UserInfo userInfo = this.userMapper.getUserInfoById(userId);
        if(null != userInfo) {
            userInfo.setPassword(null);
            return new Result(ResultCode.SUCCESS, userInfo);
        }
        return new Result(ResultCode.NOTFOUND, null);
    }

    @Override
    public Result delete(int userId) {
        if(this.userMapper.delete(userId)) {
            return new Result(ResultCode.SUCCESS, "删除成功");
        }
        return new Result(ResultCode.OPERATIONFAIL, null);
    }

    @Override
    @Transactional
    public Result editUser(UserRequest userRequest) {
        if(null != userRequest.getPassword()) {
            userRequest.setPassword(SaSecureUtil.md5(userRequest.getPassword()));
        }
        this.userMapper.editUser(userRequest);
        this.authorizeMapper.deleteUserRole(userRequest.getId());
        for(Integer roleId : userRequest.getRoles()) {
            this.authorizeMapper.addRoleToUser(roleId, userRequest.getId());
        }
        if(userRequest.getState() == 1) {   //当账号被禁用，强制登出
            StpUtil.kickout(userRequest.getId());
        }
        return new Result(ResultCode.SUCCESS, "修改成功");
    }

    @Override
    public Result changePassword(ChangePasswordRequest changePasswordRequest) {
        int userId = Integer.parseInt((String) StpUtil.getLoginId());

        ChangePasswordEntity changePassword = new ChangePasswordEntity();
        BeanUtils.copyProperties(changePasswordRequest, changePassword);
        changePassword.setId(userId);

        if(changePassword.getPassword().equals(changePassword.getConfirmPassword())) {
            changePassword.setPassword(SaSecureUtil.md5(changePassword.getPassword()));
            if(this.userMapper.changePassword(changePassword)) {
                return new Result(ResultCode.SUCCESS, "修改成功");
            }
            return new Result(ResultCode.OPERATIONFAIL, null);
        } else {
            return new Result(ResultCode.OPERATIONFAIL, "两次输入的密码不一致！");
        }
    }


}
