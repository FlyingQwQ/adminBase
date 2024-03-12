package top.kuoer.base.service;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import top.kuoer.base.common.Result;

import java.io.IOException;

public interface FileUploadService {

    /**
     * 上传文件
     * @param files 文件列表
     * @return 文件信息
     */
    Result upload(MultipartFile[] files, String flag) throws IOException;

    /**
     * 通过flag获取文件列表
     * @param flag 标记
     * @return 文件列表
     */
    Result findFileListByFlag(String flag, boolean pub);

    /**
     * 删除文件
     * @param tmpName 缓存文件名
     * @return 是否成功
     */
    Result remove(String tmpName);

}