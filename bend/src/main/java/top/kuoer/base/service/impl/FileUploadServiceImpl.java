package top.kuoer.base.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.kuoer.base.common.Result;
import top.kuoer.base.service.FileUploadService;

import java.util.Arrays;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Override
    public Result upload(MultipartFile[] files) {
        System.out.println(Arrays.toString(files));
        return null;
    }


}
