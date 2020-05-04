package com.cmh.item.biz.dao.neo4j.repository;

import com.cmh.item.biz.dao.neo4j.entity.PersonStatsDetail;
import com.cmh.item.biz.dao.neo4j.entity.intimacy;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserEventRelationRepository extends Neo4jRepository<intimacy, Long> {

    /**
     * 求用户的最短路径
     * @param startId
     * @param endId
     * @return
     */
    @Query("MATCH (p1:Person{userId:{startId}}),(p2:Person{userId:{endId}}),\n" +
            "p=shortestpath((p1)-[*..10]-(p2))\n" +
            "RETURN p")
    List<intimacy> queryShortestPath(@Param("startId") String startId, @Param("endId") String endId);

    /**
     * 求用户的一级、二级关系网
     * @param userId
     * @return
     */
    @Query("MATCH p= (:Person{userId:{userId}})-[*1..3]->() return p")
    List<intimacy> queryTargetRadiationByLevel(@Param("userId") String userId);

    /**
     * TOP-N方式求当前图形数据库中关联关系最多和热度最高的用户
     * @param
     * @return
     */
    @Query("match (n1:Person)-[r]-(n2:Person) " +
            "with n1.userId as pin,count(r) as count_r return max(count_r) as count,pin order by max(count_r) desc limit {nCount}")
    List<PersonStatsDetail> queryTopUsersByLimit(@Param("nCount") Integer nCount);
}
