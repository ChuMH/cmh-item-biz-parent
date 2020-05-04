package com.cmh.item.biz.sdk.dto.register;


import lombok.Data;

/**
 * @author：初明昊
 * @data：2019/12/09
 * @description：注册用户信息
 */
@Data
public class RegisterUserInfo {
    private Long id;
    private String name;
    private String userName;
    private String password;

    private String mail;
}
