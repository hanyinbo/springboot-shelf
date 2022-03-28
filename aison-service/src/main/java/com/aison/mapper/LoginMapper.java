package com.aison.mapper;

import com.aison.entity.TUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper
public interface LoginMapper extends BaseMapper<TUser> {
}
