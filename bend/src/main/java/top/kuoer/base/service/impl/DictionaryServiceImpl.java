package top.kuoer.base.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.kuoer.base.common.Result;
import top.kuoer.base.common.ResultCode;
import top.kuoer.base.model.entity.DictionaryEntity;
import top.kuoer.base.model.entity.DictionaryItemEntity;
import top.kuoer.base.model.vo.DictionaryVO;
import top.kuoer.base.model.vo.PaginationRequest;
import top.kuoer.base.mapper.DictionaryMapper;
import top.kuoer.base.service.DictionaryService;

import java.util.List;

@Service
public class DictionaryServiceImpl implements DictionaryService {

    private final DictionaryMapper dictionaryMapper;

    @Autowired
    public DictionaryServiceImpl(DictionaryMapper dictionaryMapper) {
        this.dictionaryMapper = dictionaryMapper;
    }

    @Override
    public Result findAllDictionary(PaginationRequest paginationRequest) {
        PageHelper.startPage(paginationRequest.getPageNum(), paginationRequest.getPageSize());
        List<DictionaryEntity> dictionaryList = this.dictionaryMapper.findAllDictionary();
        if(!dictionaryList.isEmpty()) {
            return new Result(ResultCode.SUCCESS, PageInfo.of(dictionaryList));
        }
        return new Result(ResultCode.NOTFOUND, null);
    }

    @Override
    public Result findDictionaryItemByCode(String code, PaginationRequest paginationRequest) {
        PageHelper.startPage(paginationRequest.getPageNum(), paginationRequest.getPageSize());
        List<DictionaryItemEntity> dictionaryItemList = this.dictionaryMapper.findDictionaryItemByCode(code);
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
            return new Result(ResultCode.SUCCESS, "删除成功");
        }
        return new Result(ResultCode.OPERATIONFAIL, "删除失败");
    }

    @Override
    public Result editDictionary(DictionaryVO dictionary) {
        DictionaryEntity dictionaryEntity = new DictionaryEntity();
        BeanUtils.copyProperties(dictionary, dictionaryEntity);
        if(this.dictionaryMapper.editDictionary(dictionaryEntity)) {
            return new Result(ResultCode.SUCCESS, "删除成功");
        }
        return new Result(ResultCode.OPERATIONFAIL, "删除失败");
    }

}
