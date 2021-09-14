package com.aison.mapper;

import com.aison.dto.MInfoDto;
import com.aison.dto.MInfoUnionQuery;
import com.aison.entity.MInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MInfoMapper extends BaseMapper<MInfo> {

    public List<MInfo> findAllUser();

    public List<MInfoUnionQuery> findUnionQuery();

    public List<MInfoDto> findUnionAll();

    public List<MInfoDto> findUnionAll2();
}
