package com.aison.service.impl;

import com.aison.entity.TUser;
import com.aison.mapper.TUserMapper;
import com.aison.service.TUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * TODO
 *  获取用户信息
 * @author hyb
 * @date 2021/9/18 10:59
 */
@AllArgsConstructor
@Service
@Slf4j
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements TUserService {

    private TUserMapper tUserMapper;

    /**
     * 根据ID查询用户
     * @param username
     * @return  TUser
     */
    public TUser findUserByUserName(String username) {
        LambdaQueryWrapper<TUser> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(username)) {
            wrapper.eq(TUser::getUsername,username).eq(TUser::getDelFlag,false);
        }
        return tUserMapper.selectOne(wrapper);
    }
}
