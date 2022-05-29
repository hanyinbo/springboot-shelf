package com.aison.mapper;

import com.aison.entity.NzUploadFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface NzUploadFileMapper extends BaseMapper<NzUploadFile> {

   List<NzUploadFile> getCompanyImgList(@Param("companyId") Long companyId);
}
