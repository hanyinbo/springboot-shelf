package com.aison.mapper;

import com.aison.entity.WxUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

@Mapper
public interface WxUserMapper extends BaseMapper<WxUser> {

    IPage<WxUser> getUserOfPage(Page page, @Param("wxUser") WxUser wxUser);
}
