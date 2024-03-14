package top.kuoer.base.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.kuoer.base.common.Result;
import top.kuoer.base.model.dto.FileDto;
import top.kuoer.base.model.vo.PaginationRequest;
import top.kuoer.base.service.FileService;

import java.io.IOException;

@RestController
@RequestMapping(path = "/file", method = {RequestMethod.POST})
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @RequestMapping("/upload")
    @SaCheckPermission("file.upload")
    public Result upload(@RequestParam("file") MultipartFile[] files, @RequestParam("flag") String flag) throws IOException {
        return this.fileService.upload(files, flag);
    }

    @RequestMapping("/remove")
    @SaCheckPermission("file.remove")
    public Result remove(@RequestParam("tmpName") String tmpName) {
        return this.fileService.remove(tmpName);
    }

    @RequestMapping("/findFileListByFlag")
    @SaCheckPermission("file.findFileListByFlag")
    public Result findFileListByFlag(@RequestParam("flag") String flag, boolean pub) {
        return this.fileService.findFileListByFlag(flag, pub);
    }

    @RequestMapping("/findAllFileList")
    @SaCheckPermission("file.findAllFileList")
    public Result findAllFileList(PaginationRequest paginationRequest, FileDto fileDto) {
        return this.fileService.findAllFileList(paginationRequest, fileDto);
    }

}
