package com.aison.mapper;

import com.aison.entity.TRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

import java.util.List;

/**
 *
 */
@Mapper
public interface TRoleMapper extends BaseMapper<TRole> {

    List<TRole> listRoleByUserId(Long userId);
}
