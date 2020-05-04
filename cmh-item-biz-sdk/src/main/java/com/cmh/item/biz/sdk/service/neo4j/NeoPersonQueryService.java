package com.cmh.item.biz.sdk.service.neo4j;

import com.cmh.item.biz.sdk.dto.neo4j.PersonShortestPathDto;
import com.cmh.project.basis.base.ResultBuilder;

/**
 * @author 初明昊
 * @className NeoPersonQueryService
 * @description 用户关系查询接口
 */
public interface NeoPersonQueryService {
    /**
     * 查询出两用户最短路径
     * @param shortestPathUsers
     * @return
     */
    ResultBuilder queryShortestPath(PersonShortestPathDto shortestPathUsers);

    /**
     * 查询出该用户的一级二级三级辐射图
     * @param userId
     * @return
     */
    ResultBuilder queryTargetRadiationByLevel(String userId);

    /**
     * TOP-N方式求当前图形数据库中关联关系最多和热度最高的用户
     * @param
     * @return
     */
    ResultBuilder queryTop(Integer max);
}
