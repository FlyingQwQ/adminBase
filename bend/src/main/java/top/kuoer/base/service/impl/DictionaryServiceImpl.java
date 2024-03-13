package top.kuoer.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.yulichang.base.MPJBaseMapper;
import com.github.yulichang.query.MPJQueryWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.kuoer.base.common.Result;
import top.kuoer.base.common.ResultCode;
import top.kuoer.base.mapper.DictionaryItemMapper;
import top.kuoer.base.model.entity.DictionaryEntity;
import top.kuoer.base.model.entity.DictionaryItemEntity;
import top.kuoer.base.model.vo.*;
import top.kuoer.base.mapper.DictionaryMapper;
import top.kuoer.base.service.DictionaryService;
import top.kuoer.base.utils.QueryWrapperUtils;

import java.util.List;

@Service
public class DictionaryServiceImpl implements DictionaryService {

    private final DictionaryMapper dictionaryMapper;
    private final DictionaryItemMapper dictionaryItemMapper;

    @Autowired
    public DictionaryServiceImpl(DictionaryMapper dictionaryMapper, DictionaryItemMapper dictionaryItemMapper) {
        this.dictionaryMapper = dictionaryMapper;
        this.dictionaryItemMapper = dictionaryItemMapper;
    }

    @Override
    public Result findAllDictionary(PaginationRequest paginationRequest, DictionaryRequest dictionaryRequest) {
        PageHelper.startPage(paginationRequest.getPageNum(), paginationRequest.getPageSize());
        DictionaryEntity dictionaryEntity = new DictionaryEntity();
        BeanUtils.copyProperties(dictionaryRequest, dictionaryEntity);
        QueryWrapper<DictionaryEntity> queryWrapper = new QueryWrapper<>();
        QueryWrapperUtils.autoLikeIfNotNull(queryWrapper, dictionaryEntity);
        List<DictionaryEntity> dictionaryList = this.dictionaryMapper.selectList(queryWrapper);
        if(!dictionaryList.isEmpty()) {
            return new Result(ResultCode.SUCCESS, PageInfo.of(dictionaryList));
        }
        return new Result(ResultCode.NOTFOUND, null);
    }

    @Override
    public Result findDictionaryItemByCode(String code, PaginationRequest paginationRequest, DictionaryItemRequest dictionaryItemRequest) {
        PageHelper.startPage(paginationRequest.getPageNum(), paginationRequest.getPageSize());

        DictionaryItemEntity dictionaryItemEntity = new DictionaryItemEntity();
        BeanUtils.copyProperties(dictionaryItemRequest, dictionaryItemEntity);

        MPJLambdaWrapper<DictionaryItemEntity> queryWrapper = new MPJLambdaWrapper<>();
        queryWrapper.selectAll(DictionaryItemEntity.class);
        queryWrapper.leftJoin(DictionaryEntity.class, DictionaryEntity::getId, DictionaryItemEntity::getDictionaryId);
        queryWrapper.eq(DictionaryEntity::getCode, code);
        queryWrapper.like(StringUtils.isNotBlank(dictionaryItemEntity.getKey()), DictionaryItemEntity::getKey, dictionaryItemEntity.getKey());
        queryWrapper.like(StringUtils.isNotBlank(dictionaryItemEntity.getValue()), DictionaryItemEntity::getValue, dictionaryItemEntity.getValue());
        queryWrapper.like(StringUtils.isNotBlank(dictionaryItemEntity.getDescribe()), DictionaryItemEntity::getDescribe, dictionaryItemEntity.getDescribe());

        List<DictionaryItemEntity> dictionaryItemList = this.dictionaryItemMapper.selectJoinList(DictionaryItemEntity.class, queryWrapper);
        if(!dictionaryItemList.isEmpty()) {
            return new Result(ResultCode.SUCCESS, PageInfo.of(dictionaryItemList));
        }
        return new Result(ResultCode.NOTFOUND, null);
    }

    @Override
    public Result addDictionary(DictionaryVO dictionary) {
        DictionaryEntity dictionaryEntity = new DictionaryEntity();
        BeanUtils.copyProperties(dictionary, dictionaryEntity);
        if(this.dictionaryMapper.addDictionary(dictionaryEntity)) {
            return new Result(ResultCode.SUCCESS, "添加成功");
        }
        return new Result(ResultCode.OPERATIONFAIL, "添加失败");
    }

    @Override
    public Result deleteDictionary(int id) {
        if(this.dictionaryMapper.deleteDictionary(id)) {
            this.dictionaryItemMapper.deleteAllDictionaryItemByDictionaryId(id);
            return new Result(ResultCode.SUCCESS, "删除成功");
        }
        return new Result(ResultCode.OPERATIONFAIL, "删除失败");
    }

    @Override
    public Result editDictionary(DictionaryVO dictionary) {
        DictionaryEntity dictionaryEntity = new DictionaryEntity();
        BeanUtils.copyProperties(dictionary, dictionaryEntity);
        if(this.dictionaryMapper.editDictionary(dictionaryEntity)) {
            return new Result(ResultCode.SUCCESS, "修改成功");
        }
        return new Result(ResultCode.OPERATIONFAIL, "修改失败");
    }

    @Override
    public Result deleteDictionaryItem(int id) {
        if(this.dictionaryItemMapper.deleteDictionaryItem(id)) {
            return new Result(ResultCode.SUCCESS, "删除成功");
        }
        return new Result(ResultCode.OPERATIONFAIL, "删除失败");
    }

    @Override
    public Result editDictionaryItem(DictionaryItemVO dictionaryItem) {
        DictionaryItemEntity dictionaryItemEntity = new DictionaryItemEntity();
        BeanUtils.copyProperties(dictionaryItem, dictionaryItemEntity);
        if(this.dictionaryItemMapper.editDictionaryItem(dictionaryItemEntity)) {
            return new Result(ResultCode.SUCCESS, "修改成功");
        }
        return new Result(ResultCode.OPERATIONFAIL, "修改失败");
    }

    @Override
    public Result addDictionaryItem(DictionaryItemVO dictionaryItem) {
        DictionaryItemEntity dictionaryItemEntity = new DictionaryItemEntity();
        BeanUtils.copyProperties(dictionaryItem, dictionaryItemEntity);
        if(this.dictionaryItemMapper.addDictionaryItemByCode(dictionaryItemEntity)) {
            return new Result(ResultCode.SUCCESS, "添加成功");
        }
        return new Result(ResultCode.OPERATIONFAIL, "添加失败");
    }

}
