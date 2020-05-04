package com.cmh.item.biz.service.impl.neo4j;

import com.cmh.item.biz.sdk.dto.neo4j.PersonShortestPathDto;
import com.cmh.item.biz.sdk.service.neo4j.NeoPersonQueryService;
import com.cmh.item.biz.service.business.BizNeoPersonQueryService;
import com.cmh.project.basis.base.ResultBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service("neoUserQueryService")
public class NeoPersonQueryServiceImpl implements NeoPersonQueryService {

    @Autowired
    private BizNeoPersonQueryService bizNeoPersonQuery;

    /**
     * 查询出两用户最短路径
     * @param shortestPathUsers
     * @return
     */
    @Override
    public ResultBuilder queryShortestPath(PersonShortestPathDto shortestPathUsers) {
        return bizNeoPersonQuery.queryShortestPath(shortestPathUsers);
    }

    /**
     * 查询出该用户的三级辐射图
     * @param userId
     * @return
     */
    @Override
    public ResultBuilder queryTargetRadiationByLevel(String userId) {
        return bizNeoPersonQuery.queryTargetRadiationByLevel(userId);
    }

    /**
     * TOP-N方式求当前图形数据库中关联关系最多和热度最高的用户
     * @param
     * @return
     */
    @Override
    public ResultBuilder queryTop(Integer max) {
        return bizNeoPersonQuery.queryTop(max);
    }
}
