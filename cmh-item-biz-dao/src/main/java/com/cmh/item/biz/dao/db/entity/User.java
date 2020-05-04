package com.cmh.item.biz.dao.db.entity;

import lombok.Data;

/**
 * @author：初明昊
 * @data：2020/04/24
 * @description：Mysql的User用户表映射类
 */
@Data
public class User{

    private Long id;

    private String userName;

    private String password;

    private String email;
}
