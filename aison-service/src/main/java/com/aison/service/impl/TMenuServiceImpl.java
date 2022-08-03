package com.aison.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.aison.dto.MenuTree;
import com.aison.entity.TMenu;
import com.aison.entity.TRole;
import com.aison.entity.TUser;
import com.aison.mapper.TMenuMapper;
import com.aison.service.TMenuService;
import com.aison.service.TRoleService;
import com.aison.util.MenuTreeUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单服务实现类
 */
@Slf4j
@Service
@AllArgsConstructor
public class TMenuServiceImpl extends ServiceImpl<TMenuMapper, TMenu> implements TMenuService {

    private RedisTemplate redisTemplate;
    private TRoleService tRoleService;

    @Override
    public List<MenuTree> getListMenuByRole() {
        TUser user = (TUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("获取用户："+user);
        List<Long> roleList = tRoleService.findRoleByUserId(user.getId()).stream().map(TRole::getRoleId).collect(Collectors.toList());
        List<TMenu> menuList = baseMapper.getListMenuByRoleId(roleList);
        Long parent = 0L;
        return MenuTreeUtil.buildTree(menuList,parent);
    }

    @Override
    public List<String> findAllRoleNameByPath(String path) {
        return baseMapper.findAllRoleNameByPath(path);
    }

    @Override
    public List<TMenu> getMenuListByUserId(Long id) {
        List<TMenu> menuList = (List<TMenu>) redisTemplate.opsForValue().get("menu_" + id);
        if(CollUtil.isEmpty(menuList)){
            log.info("进入数据库查询");
            menuList = baseMapper.getMenuListByUserId(id);
//            redisTemplate.opsForValue().set("menu_"+id,menuList);
        }
        log.info("菜单："+menuList.toString());
        return menuList;
    }
}
