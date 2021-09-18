package com.aison.authority;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Set;

/**
 * TODO
 *
 * @author hyb
 * @date 2021/9/18 11:35
 */
@Data
@Component
public class ManageUserDetails implements UserDetails, Serializable {

    private static final long serialVersionUID = 2422807604605329003L;

    private String username;
    private String password;
    private Set<? extends GrantedAuthority> authorities;

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
