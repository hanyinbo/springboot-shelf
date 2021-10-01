package com.aison.authority;

import com.aison.entity.TUser;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;

/**
 * TODO
 * <p>
 * 重写sercuity中UserDetails
 *
 * @author hyb
 * @date 2021/9/18 11:35
 */
@Data
@Component
public class ManageUserDetails implements UserDetails {

    public Long id;
    private String username;
    private String password;
    private String ip;
    private String role;
    /**
     * 是否记住密码
     */
    private Boolean remember;

    private Set<? extends GrantedAuthority> authorities;

    public ManageUserDetails() {

    }

    public ManageUserDetails(TUser loginUser) {
        this.id = loginUser.getId();
        this.username = loginUser.getUsername();
        this.password = loginUser.getPassword();
        this.ip=loginUser.getIp();
        this.role= loginUser.getRole();
        authorities = Collections.singleton(new SimpleGrantedAuthority(loginUser.getRole()));
    }

    /**
     * 判断账号是否已经过期，默认没有过期
     *
     * @return true 没有过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 判断账号是否被锁定，默认没有锁定
     *
     * @return true 没有锁定  false 锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * todo 判断信用凭证是否过期，默认没有过期
     *
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 判断账号是否可用，默认可用
     *
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
