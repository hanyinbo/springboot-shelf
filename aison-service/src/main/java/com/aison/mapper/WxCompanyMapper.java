package com.aison.mapper;

import com.aison.dto.WxCompanyOfPageDto;
import com.aison.entity.WxCompany;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

@Mapper
public interface WxCompanyMapper extends BaseMapper<WxCompany> {

    IPage<WxCompany> getPageOfCompany(Page page, @Param("pageDto") WxCompanyOfPageDto pageDto);
}
