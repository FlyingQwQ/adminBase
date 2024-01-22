package top.kuoer.base.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.kuoer.base.common.Result;
import top.kuoer.base.model.vo.PaginationRequest;
import top.kuoer.base.service.ResourceButtonService;

@RestController
@RequestMapping(path = "/resourcebutton", method = {RequestMethod.POST})
public class ResourceButtonController {

    private final ResourceButtonService resourceButtonService;

    @Autowired
    public ResourceButtonController(ResourceButtonService resourceButtonService) {
        this.resourceButtonService = resourceButtonService;
    }

    @RequestMapping(path = "/findAllResourceButton")
    @SaCheckPermission("ResourceButton.findAllResourceButton")
    public Result findAllResourceButton(PaginationRequest paginationRequest) {
        return this.resourceButtonService.findAllResourceButton(paginationRequest);
    }

    @RequestMapping(path = "/findUserResourceButtonByMenuId")
    @SaCheckLogin
    public Result findUserResourceButtonByMenuId(@RequestParam int menuId) {
        return this.resourceButtonService.findUserResourceButtonByMenuId(menuId);
    }

    @RequestMapping(path = "/findResourceButtonByMenuId")
    @SaCheckPermission("ResourceButton.findResourceButtonByMenuId")
    public Result findResourceButtonByMenuId(@RequestParam int menuId) {
        return this.resourceButtonService.findResourceButtonByMenuId(menuId);
    }

    @RequestMapping(path = "/addResourceButtonByMenuId")
    @SaCheckPermission("ResourceButton.addResourceButtonByMenuId")
    public Result addResourceButtonByMenuId(@RequestParam int menuId,
                                            @RequestParam int resourceId) {
        return this.resourceButtonService.addResourceButtonByMenuId(menuId, resourceId);
    }

    @RequestMapping(path = "/deleteResourceButtonByMenuId")
    @SaCheckPermission("ResourceButton.deleteResourceButtonByMenuId")
    public Result deleteResourceButtonByMenuId(@RequestParam int menuId,
                                            @RequestParam int resourceId) {
        return this.resourceButtonService.deleteResourceButtonByMenuId(menuId, resourceId);
    }

    @RequestMapping(path = "/addResourceButton")
    @SaCheckPermission("ResourceButton.addResourceButton")
    public Result addResourceButton(@RequestParam String resourceName,
                                    @RequestParam String resourceKey) {
        return this.resourceButtonService.addResourceButton(resourceName, resourceKey);
    }

    @RequestMapping(path = "/deleteResourceButton")
    @SaCheckPermission("ResourceButton.deleteResourceButton")
    public Result deleteResourceButton(@RequestParam int resourceId) {
        return this.resourceButtonService.deleteResourceButton(resourceId);
    }

    @RequestMapping(path = "/editResourceButton")
    @SaCheckPermission("ResourceButton.editResourceButton")
    public Result editResourceButton(@RequestParam int resourceId,
                                     @RequestParam String resourceName,
                                     @RequestParam String resourceKey) {
        return this.resourceButtonService.editResourceButton(resourceId, resourceName, resourceKey);
    }

}
