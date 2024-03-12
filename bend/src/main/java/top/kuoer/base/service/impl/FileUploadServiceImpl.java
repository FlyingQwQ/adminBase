package top.kuoer.base.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import top.kuoer.base.common.Result;
import top.kuoer.base.common.ResultCode;
import top.kuoer.base.mapper.FileMapper;
import top.kuoer.base.mapper.UserMapper;
import top.kuoer.base.model.dto.FileDto;
import top.kuoer.base.model.entity.FileEntity;
import top.kuoer.base.model.entity.UserInfo;
import top.kuoer.base.service.FileUploadService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${file.upload.url}")
    private String uploadFilePath;
    private final FileMapper fileMapper;
    private final UserMapper userMapper;

    @Autowired
    public FileUploadServiceImpl(FileMapper fileMapper, UserMapper userMapper) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public Result upload(MultipartFile[] files, String flag) throws IOException {
        int userId = Integer.parseInt((String) StpUtil.getLoginId());
        UserInfo userInfo = this.userMapper.findUserInfoById(userId);

        List<FileEntity> fileEntityList = new ArrayList<>();

        for(MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            String suffix = fileName.split("\\.")[fileName.split("\\.").length - 1];
            String tmpName = SaSecureUtil.md5(fileName + System.currentTimeMillis()) + "." + suffix;

            File dest = new File(this.uploadFilePath +'/'+ tmpName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            file.transferTo(dest);

            FileDto fileDto = new FileDto();
            fileDto.setFileName(fileName);
            fileDto.setTmpName(tmpName);
            fileDto.setUserId(userId);
            fileDto.setCreateTime(System.currentTimeMillis() / 1000);
            fileDto.setFlag(flag);

            FileEntity fileEntity = new FileEntity();
            BeanUtils.copyProperties(fileDto, fileEntity);
            fileEntityList.add(fileEntity);
        }

        List<FileDto> resultFileDto = new ArrayList<>();
        for(FileEntity fileEntity : fileEntityList) {
            FileDto fileDto = new FileDto();
            BeanUtils.copyProperties(fileEntity, fileDto);
            fileDto.setUserName(userInfo.getUsername());
            resultFileDto.add(fileDto);
        }

        if(this.fileMapper.saveFiles(fileEntityList)) {
            return new Result(ResultCode.SUCCESS, resultFileDto);
        }

        return new Result(ResultCode.OPERATIONFAIL, "上传失败");
    }

    @Override
    public Result findFileListByFlag(String flag, boolean pub) {
        List<FileEntity> fileEntityList = null;
        if(pub) {
            fileEntityList = this.fileMapper.findFileListByFlag(flag);
        } else {
            int userId = Integer.parseInt((String) StpUtil.getLoginId());
            fileEntityList = this.fileMapper.findFileListByFlagAndUserId(flag, userId);
        }
        if(null != fileEntityList) {
            return new Result(ResultCode.SUCCESS, fileEntityList);
        }
        return new Result(ResultCode.OPERATIONFAIL, "获取失败");
    }

    @Override
    public Result remove(String tmpName) {
        File tempFile = new File(this.uploadFilePath +'/'+ tmpName);
        if(tempFile.delete()) {
            this.fileMapper.removeFile(tmpName);
            return new Result(ResultCode.SUCCESS, "删除成功");
        }
        return new Result(ResultCode.OPERATIONFAIL, "删除失败");
    }


}
