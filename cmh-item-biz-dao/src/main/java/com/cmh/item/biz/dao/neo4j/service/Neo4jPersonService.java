package com.cmh.item.biz.dao.neo4j.service;

import com.cmh.item.biz.dao.neo4j.entity.PersonStatsDetail;
import com.cmh.item.biz.dao.neo4j.entity.intimacy;
import com.cmh.item.biz.dao.neo4j.repository.UserEventRelationRepository;
import com.cmh.project.basis.utils.json.FastJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class Neo4jPersonService {

    @Autowired
    private UserEventRelationRepository userEventRelationRepository;

    /**
     * 求用户的最短路径
     * @param startId
     * @param endId
     * @return
     */
    public List<intimacy> queryShortestPath(String startId, String endId){
        List<intimacy> personEventRelations = userEventRelationRepository.queryShortestPath(startId, endId);
        log.info("===>#Neo4jPersonService.queryShortestPath,userEventRelations={}", FastJsonUtil.obj2json(personEventRelations));
        return personEventRelations;
    }

    /**
     * 求用户的三级关系网
     * @param userId
     * @return
     */
    public List<intimacy> queryTargetRadiationByLevel(String userId){
        List<intimacy> relations = userEventRelationRepository.queryTargetRadiationByLevel(userId);
        log.info("===>#Neo4jPersonService.queryTargetRadiationByLevel,relations={},userId={}",FastJsonUtil.obj2json(relations),userId);
        return relations;
    }

    /**
     * TOP-N方式求当前图形数据库中关联关系最多和热度最高的用户
     * @param
     * @return
     */
    public List<PersonStatsDetail> queryTopUsersByLimit(Integer limit){
        if(limit==null){
            limit = 1;
        }
        List<PersonStatsDetail> userStatsDetails = userEventRelationRepository.queryTopUsersByLimit(limit);
        log.info("===>#Neo4jPersonService.queryTopUsersByLimit,userStatsDetails={}",FastJsonUtil.obj2json(userStatsDetails));
        return userStatsDetails;
    }
}
