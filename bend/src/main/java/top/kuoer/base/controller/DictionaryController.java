package top.kuoer.base.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.kuoer.base.common.Result;
import top.kuoer.base.entity.PaginationRequest;
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



}
