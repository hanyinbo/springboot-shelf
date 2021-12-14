package com.aison.service;

import com.aison.entity.TMenu;
import com.aison.vo.TMenuRoleVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 菜单服务类
 */
public interface TMenuService extends IService<TMenu> {
    List<TMenuRoleVO> getListMenuByRoleId(Long roleId);

    List<String> findAllRoleNameByPath(String path);
}
