package com.aison.mapper;

import com.aison.entity.TMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/*
 *
 */
public interface TMenuMapper extends BaseMapper<TMenu> {
    List<TMenu> getListMenuByRoleId(@Param("roleIds") List<Long> roleIds);

    List<String> findAllRoleNameByPath(String path);
}
