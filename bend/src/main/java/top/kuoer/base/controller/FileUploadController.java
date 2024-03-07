package top.kuoer.base.controller;

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
    public Result upload(@RequestParam("file") MultipartFile files[]) throws IOException {
        return this.fileUploadService.upload(files);
    }


}
