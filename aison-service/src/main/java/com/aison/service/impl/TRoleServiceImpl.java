package com.aison.service.impl;

import com.aison.entity.TRole;
import com.aison.mapper.TRoleMapper;
import com.aison.service.TRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TRoleServiceImpl extends ServiceImpl<TRoleMapper,TRole> implements TRoleService {

    private final TRoleMapper roleMapper;

    @Override
    public List<TRole> findRoleByUserId(Long userId) {
        return roleMapper.listRoleByUserId(userId);
    }
}
