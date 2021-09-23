package com.aison.dto;

import com.aison.entity.TUser;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * TODO
 *
 * @author hyb
 * @date 2021/9/22 15:04
 */
@Data
public class UserDTO  implements UserDetails {
   private Long id;
    /**
     * 是否记住密码
     */
    private Boolean remember;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户密码
     */
    private String userPassword;

    private Collection<? extends GrantedAuthority> authorities;


    public UserDTO(TUser loginUser){
        this.id = loginUser.getId();
        this.userName = loginUser.getUsername();
        this.userPassword = loginUser.getPassword();
        authorities = Collections.singleton(new SimpleGrantedAuthority(loginUser.getRole()));
    }
    @Override
    public String getPassword() {
        return userPassword;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return authorities;
    }
}
