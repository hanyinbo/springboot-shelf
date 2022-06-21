package com.aison.entity;

import com.aison.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@TableName(value = "t_user")
@Data
@ApiModel(value = "用户表", description = "系统用户表")
public class TUser extends BaseEntity  implements UserDetails {
    @TableField(value = "id")
    @TableId
    @ApiModelProperty(value = "用户表主键")
    private Long id;
    @TableField(value = "username")
    @ApiModelProperty(value = "用户名")
    private String username;
    @TableField(value = "password")
    @ApiModelProperty(value = "密码")
    private String password;
    @TableField(value = "fullname")
    @ApiModelProperty(value = "全称")
    private String fullname;
    @TableField(value = "mobile")
    @ApiModelProperty(value = "手机号")
    private String mobile;
    @TableField(value = "enable")
    @ApiModelProperty(value = "是否启用")
    private Boolean enable;
    @TableField(value = "del_flag")
    @ApiModelProperty(value = "0-正常，1-删除")
    private Boolean delFlag;
    @TableField(value = "ip")
    @ApiModelProperty(value = "用户IP")
    private String ip;
    @TableField(exist = false)
    @ApiModelProperty(value = "角色")
    private List<TRole> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
}
