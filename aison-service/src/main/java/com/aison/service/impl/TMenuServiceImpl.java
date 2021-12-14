package com.aison.service.impl;

import com.aison.entity.TMenu;
import com.aison.mapper.TMenuMapper;
import com.aison.service.TMenuService;
import com.aison.vo.TMenuRoleVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 菜单服务实现类
 */
@Service
@AllArgsConstructor
public class TMenuServiceImpl extends ServiceImpl<TMenuMapper, TMenu> implements TMenuService {
    @Override
    public List<TMenuRoleVO> getListMenuByRoleId(Long roleId) {
        return baseMapper.getListMenuByRoleId(roleId);
    }

    @Override
    public List<String> findAllRoleNameByPath(String path) {
        return baseMapper.findAllRoleNameByPath(path);
    }
}
