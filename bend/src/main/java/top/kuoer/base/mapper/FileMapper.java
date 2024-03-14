package top.kuoer.base.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import top.kuoer.base.model.entity.FileEntity;

import java.util.List;

@Mapper
@Repository
public interface FileMapper extends MPJBaseMapper<FileEntity> {

    boolean saveFiles(@Param("fileEntityList") List<FileEntity> fileEntityList);

    @Select("select * from files where flag = #{flag}")
    @Results({
            @Result(property = "fileName", column = "file_name"),
            @Result(property = "tmpName", column = "tmp_name"),
            @Result(property = "createTime", column = "create_time")
    })
    List<FileEntity> findFileListByFlag(@Param("flag") String flag);

    @Select("select * from files where flag = #{flag} and user_id = #{userId}")
    @Results({
            @Result(property = "fileName", column = "file_name"),
            @Result(property = "tmpName", column = "tmp_name"),
            @Result(property = "createTime", column = "create_time")
    })
    List<FileEntity> findFileListByFlagAndUserId(@Param("flag") String flag, @Param("userId") int userId);

    @Delete("delete from files where tmp_name=#{tmpName}")
    boolean removeFile(@Param("tmpName") String tmpName);

}
