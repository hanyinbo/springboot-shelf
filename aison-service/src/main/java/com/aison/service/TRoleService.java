package com.aison.service;

import com.aison.entity.TRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 角色服务类
 */
public interface TRoleService extends IService<TRole> {
    public List<TRole> findRoleByUserId(Long userId);
}
