package com.cmh.item.biz.dao.neo4j.entity;

import lombok.Data;
import org.springframework.data.neo4j.annotation.QueryResult;

/**
 * @author：初明昊
 * @data：2020/04/29
 * @description：用户关系强度回收收集数据
 */
@Data
@QueryResult
public class PersonStatsDetail {

    private String pin;

    private Integer count;
}
