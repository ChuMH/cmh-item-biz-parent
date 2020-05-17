package com.cmh.item.biz.sdk.dto.neo4j;

import lombok.Data;

import java.io.Serializable;

/**
 * @author：
 * @data：
 * @description：
 */
@Data
public class PersonDto implements Serializable {

    private Long id;

    private String name;

    private String userId;

    private String age;

    private String hobby;

    private Long degree;
}
