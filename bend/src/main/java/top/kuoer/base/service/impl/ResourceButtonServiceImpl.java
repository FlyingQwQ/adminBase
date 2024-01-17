package top.kuoer.base.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.kuoer.base.common.Result;
import top.kuoer.base.common.ResultCode;
import top.kuoer.base.entity.PaginationRequest;
import top.kuoer.base.entity.ResourceButtonEntity;
import top.kuoer.base.mapper.ResourceButtonMapper;
import top.kuoer.base.service.ResourceButtonService;

import java.util.List;

@Service
public class ResourceButtonServiceImpl implements ResourceButtonService {

    private final ResourceButtonMapper resourceButtonMapper;

    @Autowired
    public ResourceButtonServiceImpl(ResourceButtonMapper resourceButtonMapper) {
        this.resourceButtonMapper = resourceButtonMapper;
    }

    @Override
    public Result findAllResourceButton(PaginationRequest paginationRequest) {
        PageHelper.startPage(paginationRequest.getPageNum(), paginationRequest.getPageSize());
        List<ResourceButtonEntity> ResourceButtonList = this.resourceButtonMapper.findAllResourceButton();
        if(!ResourceButtonList.isEmpty()) {
            return new Result(ResultCode.SUCCESS, PageInfo.of(ResourceButtonList));
        }
        return new Result(ResultCode.NOTFOUND, null);
    }

    @Override
    public Result findResourceButtonByMenuId(int menuId) {
        List<ResourceButtonEntity> ResourceButtonList = this.resourceButtonMapper.findResourceButtonByMenuId(menuId);
        if(!ResourceButtonList.isEmpty()) {
            return new Result(ResultCode.SUCCESS, ResourceButtonList);
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
