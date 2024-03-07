package top.kuoer.base.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.kuoer.base.common.Result;
import top.kuoer.base.service.FileUploadService;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${file.upload.url}")
    private String uploadFilePath;

    @Override
    public Result upload(MultipartFile[] files) throws IOException {
        for(MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            File dest = new File(uploadFilePath +'/'+ fileName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            file.transferTo(dest);
        }

        return null;
    }


}
