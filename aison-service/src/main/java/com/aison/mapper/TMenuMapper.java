package com.aison.mapper;

import com.aison.entity.TMenu;
import com.aison.vo.TMenuRoleVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/*
 *
 */
@Mapper
public interface TMenuMapper extends BaseMapper<TMenu> {
    List<TMenuRoleVO> getListMenuByRoleId(Long roleId);

    List<String> findAllRoleNameByPath(String path);
}
