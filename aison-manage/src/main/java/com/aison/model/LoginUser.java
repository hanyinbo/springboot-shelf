package com.aison.model;

import lombok.Data;

/**
 * TODO
 *
 * @author hyb
 * @date 2021/9/23 14:06
 */
@Data
public class LoginUser {


    private String username;
    private String password;
    private Integer rememberMe;

}
