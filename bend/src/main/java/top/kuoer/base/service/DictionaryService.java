package top.kuoer.base.service;

import top.kuoer.base.common.Result;
import top.kuoer.base.model.vo.*;

public interface DictionaryService {

    /**
     * 查询所有字典
     * @param paginationRequest 分页查询
     * @return 字典列表
     */
    Result findAllDictionary(PaginationRequest paginationRequest, DictionaryRequest dictionaryRequest);

    /**
     * 通过编码查询数据字典项
     * @param code 字典编码
     * @param paginationRequest 分页
     * @return 数据项列表
     */
    Result findDictionaryItemByCode(String code, PaginationRequest paginationRequest, DictionaryItemRequest dictionaryItemRequest);

    /**
     * 添加新字典
     * @param dictionary 字典信息
     * @return 是否成功
     */
    Result addDictionary(DictionaryVO dictionary);

    /**
     * 删除字典
     * @param id 字典ID
     * @return 是否成功
     */
    Result deleteDictionary(int id);

    /**
     * 编辑字典
     * @param dictionary 字典信息
     * @return 是否成功
     */
    Result editDictionary(DictionaryVO dictionary);


    /**
     * 删除字典项
     * @param id 字典项ID
     * @return 是否成功
     */
    Result deleteDictionaryItem(int id);

    /**
     * 编辑字典项
     * @param dictionaryItem 字典项信息
     * @return 是否成功
     */
    Result editDictionaryItem(DictionaryItemVO dictionaryItem);

    /**
     * 添加字典项
     * @param dictionaryItem 字典项信息
     * @return 是否成功
     */
    Result addDictionaryItem(DictionaryItemVO dictionaryItem);

}
