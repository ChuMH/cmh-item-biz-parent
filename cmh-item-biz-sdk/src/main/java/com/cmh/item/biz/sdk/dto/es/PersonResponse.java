package com.cmh.item.biz.sdk.dto.es;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PersonResponse implements Serializable {

    private String name;

    private Integer age;

    private Date birthday;

    private String hobby;

    private AddressResponse address;
}
