package com.aison.mapper;

import com.aison.entity.WxPosition;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

@Mapper
public interface WxPositionMapper extends BaseMapper<WxPosition> {

    IPage<WxPosition> getPositionOfPage(Page page, @Param("wxPosition") WxPosition wxPosition);
}
