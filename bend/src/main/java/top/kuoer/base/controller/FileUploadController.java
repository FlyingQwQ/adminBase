package top.kuoer.base.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.kuoer.base.common.Result;
import top.kuoer.base.service.FileUploadService;

import java.io.IOException;

@RestController
@RequestMapping(path = "/fileUpload", method = {RequestMethod.POST})
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @Autowired
    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @RequestMapping("/upload")
    @SaCheckPermission("file.upload")
    public Result upload(@RequestParam("file") MultipartFile[] files, @RequestParam("flag") String flag) throws IOException {
        return this.fileUploadService.upload(files, flag);
    }

    @RequestMapping("/remove")
    @SaCheckPermission("file.remove")
    public Result remove(@RequestParam("tmpName") String tmpName) {
        return this.fileUploadService.remove(tmpName);
    }

    @RequestMapping("/findFileListByFlag")
    @SaCheckPermission("file.findFileListByFlag")
    public Result findFileListByFlag(@RequestParam("flag") String flag, boolean pub) {
        return this.fileUploadService.findFileListByFlag(flag, pub);
    }

}
