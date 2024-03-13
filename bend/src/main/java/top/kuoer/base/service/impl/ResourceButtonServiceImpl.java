package top.kuoer.base.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.kuoer.base.common.Result;
import top.kuoer.base.common.ResultCode;
import top.kuoer.base.mapper.AuthorizeMapper;
import top.kuoer.base.mapper.ResourceButtonMapper;
import top.kuoer.base.mapper.RolesMapper;
import top.kuoer.base.model.entity.Permission;
import top.kuoer.base.model.vo.PaginationRequest;
import top.kuoer.base.model.entity.ResourceButtonEntity;
import top.kuoer.base.model.entity.Role;
import top.kuoer.base.model.entity.RoleMenuResourceButtonQuery;
import top.kuoer.base.model.vo.ResourceButtonRequest;
import top.kuoer.base.service.ResourceButtonService;
import top.kuoer.base.utils.QueryWrapperUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ResourceButtonServiceImpl implements ResourceButtonService {

    private final ResourceButtonMapper resourceButtonMapper;
    private final AuthorizeMapper authorizeMapper;
    private final RolesMapper rolesMapper;

    @Autowired
    public ResourceButtonServiceImpl(ResourceButtonMapper resourceButtonMapper, AuthorizeMapper authorizeMapper, RolesMapper rolesMapper) {
        this.resourceButtonMapper = resourceButtonMapper;
        this.authorizeMapper = authorizeMapper;
        this.rolesMapper = rolesMapper;
    }

    @Override
    public Result findAllResourceButton(PaginationRequest paginationRequest, ResourceButtonRequest resourceButtonRequest) {
        PageHelper.startPage(paginationRequest.getPageNum(), paginationRequest.getPageSize());
        ResourceButtonEntity resourceButton = new ResourceButtonEntity();
        BeanUtils.copyProperties(resourceButtonRequest, resourceButton);
        QueryWrapper<ResourceButtonEntity> queryWrapper = new QueryWrapper<>();
        QueryWrapperUtils.autoLikeIfNotNull(queryWrapper, resourceButton);
        List<ResourceButtonEntity> ResourceButtonList = this.resourceButtonMapper.selectList(queryWrapper);
        if(!ResourceButtonList.isEmpty()) {
            return new Result(ResultCode.SUCCESS, PageInfo.of(ResourceButtonList));
        }
        return new Result(ResultCode.NOTFOUND, null);
    }

    @Override
    public Result findUserResourceButtonByMenuId(int menuId) {
        int userId = Integer.parseInt((String) StpUtil.getLoginId());
        List<Role> roles = this.rolesMapper.findRolesByUserId(userId);

        List<ResourceButtonEntity> resourceButtonList = this.resourceButtonMapper.findResourceButtonByMenuId(menuId);
        Set<ResourceButtonEntity> roleResourceButtonSet = new HashSet<>();

        for(Role role : roles) {
            List<RoleMenuResourceButtonQuery> roleMenuResourceButtonQueries = this.authorizeMapper.findRoleMenuResourceButtonByRoleId(role.getId());
            for(ResourceButtonEntity resourceButton : resourceButtonList) {
                for(RoleMenuResourceButtonQuery roleMenuResourceButtonQuery : roleMenuResourceButtonQueries) {
                    if(resourceButton.getId() == roleMenuResourceButtonQuery.getResourceId() && roleMenuResourceButtonQuery.getMenuId() == menuId) {
                        roleResourceButtonSet.add(resourceButton);
                    }
                }
            }
        }

        List<ResourceButtonEntity> roleResourceButtonList = new ArrayList<>(roleResourceButtonSet);
        if(!roleResourceButtonList.isEmpty()) {
            return new Result(ResultCode.SUCCESS, roleResourceButtonList);
        }
        return new Result(ResultCode.NOTFOUND, null);
    }

    @Override
    public Result findResourceButtonByMenuId(int menuId) {
        List<ResourceButtonEntity> resourceButtonList = this.resourceButtonMapper.findResourceButtonByMenuId(menuId);
        if(!resourceButtonList.isEmpty()) {
            return new Result(ResultCode.SUCCESS, resourceButtonList);
        }
        return new Result(ResultCode.NOTFOUND, null);
    }

    @Override
    public Result addResourceButtonByMenuId(int menuId, int resourceId) {
        if(this.resourceButtonMapper.addResourceButtonByMenuId(menuId, resourceId)) {
            return new Result(ResultCode.SUCCESS, "添加成功");
        }
        return new Result(ResultCode.OPERATIONFAIL, null);
    }

    @Override
    public Result deleteResourceButtonByMenuId(int menuId, int resourceId) {
        if(this.resourceButtonMapper.deleteResourceButtonByMenuId(menuId, resourceId)) {
            return new Result(ResultCode.SUCCESS, "删除成功");
        }
        return new Result(ResultCode.OPERATIONFAIL, null);
    }

    @Override
    public Result addResourceButton(String resourceName, String resourceKey) {
        if(this.resourceButtonMapper.addResourceButton(resourceName, resourceKey)) {
            return new Result(ResultCode.SUCCESS, "添加成功");
        }
        return new Result(ResultCode.OPERATIONFAIL, null);
    }

    @Override
    public Result deleteResourceButton(int resourceId) {
        if(this.resourceButtonMapper.deleteResourceButton(resourceId)) {
            return new Result(ResultCode.SUCCESS, "删除成功");
        }
        return new Result(ResultCode.OPERATIONFAIL, null);
    }

    @Override
    public Result editResourceButton(int resourceId, String resourceName, String resourceKey) {
        if(this.resourceButtonMapper.editResourceButton(resourceId, resourceName, resourceKey)) {
            return new Result(ResultCode.SUCCESS, "修改成功");
        }
        return new Result(ResultCode.OPERATIONFAIL, null);
    }


}
