package com.cmh.item.biz.sdk.dto.neo4j;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 * 查询出两用户最短路径
 * @note
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonShortestPathDto implements Serializable {
    /**
     * 用户id
     */
    private String startId;
    /**
     * 目标用户id
     */
    private String targetId;
}
