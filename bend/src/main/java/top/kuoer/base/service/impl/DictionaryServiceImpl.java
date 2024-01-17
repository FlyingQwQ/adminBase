package top.kuoer.base.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.kuoer.base.common.Result;
import top.kuoer.base.common.ResultCode;
import top.kuoer.base.entity.DictionaryEntity;
import top.kuoer.base.entity.DictionaryItemEntity;
import top.kuoer.base.entity.PaginationRequest;
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


}
