package com.cmh.item.biz.dao.neo4j.entity;

import lombok.Data;
import org.neo4j.ogm.annotation.*;

@Data
@RelationshipEntity(type = "intimacy")
public class intimacy {
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 开始节点
     */
    @StartNode
    private Person startNode;


    @Property(name = "degree")
    private Long degree;

    /**
     * 结束节点
     */
    @EndNode
    private Person endNode;



}
