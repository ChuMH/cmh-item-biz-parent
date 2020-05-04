package com.cmh.item.biz.sdk.dto.es;


import lombok.Data;

import java.io.Serializable;

@Data
public class AddressResponse implements Serializable {

    private String province;

    private String city;
}
