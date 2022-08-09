package com.aison.service;

import com.aison.common.Result;
import com.aison.dto.MenuTree;
import com.aison.entity.TMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 菜单服务类
 */
public interface TMenuService extends IService<TMenu> {
    public Result getListMenuByRole();

    public  List<String> findAllRoleNameByPath(String path);

    public List<TMenu> getMenuWithRole();

}
