package com.aison.mapper;

import com.aison.entity.MInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MInfoMapper extends BaseMapper<MInfo> {

    public List<MInfo> findAllUser();
}
