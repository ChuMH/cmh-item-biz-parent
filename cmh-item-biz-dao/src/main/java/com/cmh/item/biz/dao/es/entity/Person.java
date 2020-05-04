package com.cmh.item.biz.dao.es.entity;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Person implements Serializable {

    private String name;

    private Integer age;

    private String hobby;

    private Date birthday;

    private Address address;
}
