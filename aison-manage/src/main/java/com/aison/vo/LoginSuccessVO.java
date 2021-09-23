package com.aison.vo;

import lombok.Data;

import java.util.List;

/**
 * TODO
 *
 * @author hyb
 * @date 2021/9/22 15:08
 */
@Data
public class LoginSuccessVO {

    /**
     * 用户编号
     */
    private String userId;

    /**
     * 用户手机号码
     */
    private String userPhone;

    /**
     * 角色信息
     */
    private List<String> roles;

    /**
     * 用户名
     */
    private String name;
}
