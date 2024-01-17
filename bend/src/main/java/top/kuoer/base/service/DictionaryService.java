package top.kuoer.base.service;

import top.kuoer.base.common.Result;
import top.kuoer.base.entity.PaginationRequest;

public interface DictionaryService {

    /**
     * 查询所有字典
     * @param paginationRequest 分页查询
     * @return 字典列表
     */
    Result findAllDictionary(PaginationRequest paginationRequest);

    /**
     * 通过编码查询数据字典项
     * @param code 字典编码
     * @param paginationRequest 分页
     * @return 数据项列表
     */
    Result findDictionaryItemByCode(String code, PaginationRequest paginationRequest);

}
