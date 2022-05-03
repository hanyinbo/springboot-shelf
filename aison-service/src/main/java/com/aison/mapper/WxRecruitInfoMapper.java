package com.aison.mapper;

import com.aison.dto.WxRecruitQueryDto;
import com.aison.entity.WxRecruitInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

@Mapper
public interface WxRecruitInfoMapper extends BaseMapper<WxRecruitInfo> {

    IPage<WxRecruitQueryDto> getRecruitOfPage(Page page, @Param("dto") WxRecruitQueryDto dto);
}
