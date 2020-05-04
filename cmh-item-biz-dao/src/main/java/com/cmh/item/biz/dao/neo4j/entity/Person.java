package com.cmh.item.biz.dao.neo4j.entity;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@NodeEntity(label = "Person")
@Data
public class Person {
    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "name")
    private String name;

    @Property(name = "userId")
    private String userId;

    @Property(name = "age")
    private String age;

    @Property(name = "hobby")
    private String hobby;
}
