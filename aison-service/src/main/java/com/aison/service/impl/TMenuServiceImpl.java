package com.aison.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.aison.common.Result;
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
import org.checkerframework.checker.units.qual.A;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public Result getListMenuByRole() {
        TUser user = (TUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Long> roleList =user.getRoles().stream().map(TRole::getRoleId).collect(Collectors.toList());
        String roles = roleList.stream().map(labelId -> labelId+"").collect(Collectors.joining(","));
        List<MenuTree> menuTrees = (List<MenuTree>) redisTemplate.opsForValue().get("menu_" + roles);
        if(CollUtil.isEmpty(menuTrees)){
            List<TMenu> menuList = baseMapper.getListMenuByRoleId(roleList);
            menuTrees = MenuTreeUtil.buildTree(menuList,  0L);
            menuTrees.stream().filter(tMenu->tMenu.getChildren().size()>0).forEach(menu ->{
                menu.setHasChildren(true);
            });
            redisTemplate.opsForValue().set("menu_"+roles,menuTrees);
        }
        return Result.buildOk(menuTrees);
    }

    @Override
    public List<String> findAllRoleNameByPath(String path) {
        return baseMapper.findAllRoleNameByPath(path);
    }

    @Override
    public List<TMenu> getMenuWithRole() {
        List<TMenu>  tMenuList= new ArrayList<>();
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if( user instanceof  TUser){
            List<Long> roleList = tRoleService.findRoleByUserId(((TUser) user).getId()).stream().map(TRole::getRoleId).collect(Collectors.toList());
            tMenuList =  baseMapper.getMenuWithRole(roleList);
        }
        return tMenuList;
    }
}
