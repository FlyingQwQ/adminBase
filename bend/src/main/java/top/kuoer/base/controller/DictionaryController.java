package top.kuoer.base.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.kuoer.base.common.Result;
import top.kuoer.base.model.vo.DictionaryItemVO;
import top.kuoer.base.model.vo.DictionaryVO;
import top.kuoer.base.model.vo.PaginationRequest;
import top.kuoer.base.service.DictionaryService;

@RestController
@RequestMapping(path = "/dictionary", method = {RequestMethod.POST})
public class DictionaryController {

    private final DictionaryService dictionaryService;

    @Autowired
    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @RequestMapping(path = "/findAllDictionary")
    @SaCheckPermission("authorize.findAllDictionary")
    public Result findAllDictionary(PaginationRequest paginationRequest) {
        return this.dictionaryService.findAllDictionary(paginationRequest);
    }

    @RequestMapping(path = "/findDictionaryItemByCode")
    @SaCheckLogin
    public Result findDictionaryItemByCode(@RequestParam String code, PaginationRequest paginationRequest) {
        return this.dictionaryService.findDictionaryItemByCode(code, paginationRequest);
    }

    @RequestMapping(path = "/addDictionary")
    @SaCheckPermission("authorize.addDictionary")
    public Result addDictionary(DictionaryVO dictionary) {
        return this.dictionaryService.addDictionary(dictionary);
    }

    @RequestMapping(path = "/deleteDictionary")
    @SaCheckPermission("authorize.deleteDictionary")
    public Result deleteDictionary(@RequestParam int id) {
        return this.dictionaryService.deleteDictionary(id);
    }

    @RequestMapping(path = "/editDictionary")
    @SaCheckPermission("authorize.editDictionary")
    public Result editDictionary(DictionaryVO dictionary) {
        return this.dictionaryService.editDictionary(dictionary);
    }


    @RequestMapping(path = "/deleteDictionaryItem")
    @SaCheckPermission("authorize.deleteDictionaryItem")
    public Result deleteDictionaryItem(@RequestParam int id) {
        return this.dictionaryService.deleteDictionaryItem(id);
    }

    @RequestMapping(path = "/editDictionaryItem")
    @SaCheckPermission("authorize.editDictionaryItem")
    public Result editDictionaryItem(DictionaryItemVO dictionaryItem) {
        return this.dictionaryService.editDictionaryItem(dictionaryItem);
    }

    @RequestMapping(path = "/addDictionaryItem")
    @SaCheckPermission("authorize.addDictionaryItem")
    public Result addDictionaryItem(DictionaryItemVO dictionaryItem) {
        return this.dictionaryService.addDictionaryItem(dictionaryItem);
    }
}
