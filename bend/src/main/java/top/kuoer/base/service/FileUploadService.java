package top.kuoer.base.service;

import org.springframework.web.multipart.MultipartFile;
import top.kuoer.base.common.Result;

public interface FileUploadService {

    /**
     * 上传文件
     * @param files 文件列表
     * @return 文件信息
     */
    Result upload(MultipartFile files[]);

}
