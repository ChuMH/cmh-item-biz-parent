package com.cmh.item.biz.service.business;

import com.cmh.item.biz.dao.neo4j.entity.Person;
import com.cmh.item.biz.dao.neo4j.entity.PersonStatsDetail;
import com.cmh.item.biz.dao.neo4j.entity.intimacy;
import com.cmh.item.biz.dao.neo4j.service.Neo4jPersonService;
import com.cmh.item.biz.sdk.dto.neo4j.PersonDto;
import com.cmh.item.biz.sdk.dto.neo4j.PersonShortestPathDto;
import com.cmh.project.basis.base.ResultBuilder;
import com.cmh.project.basis.base.constant.SysResultCode;
import com.cmh.project.basis.utils.json.FastJsonUtil;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author：初明昊
 * @data：2020/04/20
 * @description：neo4j服务业务实现
 */
@Slf4j
@Service
public class BizNeoPersonQueryService {
    @Autowired
    private Neo4jPersonService neo4jPersonService;

    /**
     * 查询出两用户最短路径
     * @param shortestPathUsers
     * @return
     */
    public ResultBuilder queryShortestPath(PersonShortestPathDto shortestPathUsers) {
        try {
            List<intimacy> shortestPathList =  neo4jPersonService.queryShortestPath(shortestPathUsers.getStartId(), shortestPathUsers.getTargetId());
            log.info(FastJsonUtil.obj2json(shortestPathList));
            Map result = translatePersonRelationDto(shortestPathList);
            return ResultBuilder.success(Optional.ofNullable(result).orElse(new LinkedHashMap(0)));
        } catch (Exception e) {
            log.error("===>BizNeoPersonQueryService#queryShortestPath,e", e);
            return ResultBuilder.failure(SysResultCode.SYS_SERVER_ERROR);
        }
    }



    /**
     * 查询出该用户的一级二级三级辐射图
     * @param userId
     * @return
     */
    public ResultBuilder queryTargetRadiationByLevel(String userId) {
        //入参判空校验
        Preconditions.checkArgument(StringUtils.isNotEmpty(userId),"userId不能为空");
        List<intimacy> intimacyList = null;
        try {
            intimacyList = neo4jPersonService.queryTargetRadiationByLevel(userId);
            log.info(FastJsonUtil.obj2json(intimacyList));
            Map<String, Object> res = translatePersonRelationDto(intimacyList);
            return ResultBuilder.success(Optional.ofNullable(res).orElse(new LinkedHashMap<>(0)));
        }catch (Exception e){
            log.error("===>BizNeoPersonQueryService#queryTargetRadiationByLevel,e", e);
            return ResultBuilder.failure(SysResultCode.SYS_SERVER_ERROR);
        }
    }

    /**
     * TOP-N方式求当前图形数据库中关联关系最多和热度最高的用户
     * @param
     * @return
     */
    public ResultBuilder queryTop(Integer max) {
        try {
            List<PersonStatsDetail> intimacyList = neo4jPersonService.queryTopUsersByLimit(max);
            return ResultBuilder.success(Optional.ofNullable(intimacyList).orElse(new ArrayList<PersonStatsDetail>(0)));
        }catch (Exception e){
            log.error("===>BizNeoPersonQueryService#queryTop,e", e);
            return ResultBuilder.failure(SysResultCode.SYS_SERVER_ERROR);
        }
    }

    /**
     * 最短路径返回值数据清洗
     * @param personRelationDtos
     * @return
     */
    private Map<String, Object> translatePersonRelationDto(List<intimacy> personRelationDtos) {
        Map<String, PersonDto> nodeMap = new LinkedHashMap<>();
        Map<String, Object> map = new HashMap<>();
        for(intimacy intimacy : personRelationDtos){
            Person start = intimacy.getStartNode();
            Person end = intimacy.getEndNode();
            Long degree = intimacy.getDegree();

            PersonDto startNode = new PersonDto();
            startNode.setId(start.getId());
            startNode.setUserId(start.getUserId());
            startNode.setName(start.getName());
            startNode.setAge(start.getAge());
            startNode.setHobby(start.getHobby());
            startNode.setDegree(degree);

            PersonDto endNode = new PersonDto();
            endNode.setId(end.getId());
            endNode.setUserId(end.getUserId());
            endNode.setName(end.getName());
            endNode.setAge(end.getAge());
            endNode.setHobby(end.getHobby());
            endNode.setDegree(degree);

            if (!nodeMap.containsKey(startNode.getName())) {
                nodeMap.put(startNode.getName(), startNode);
            }
            if (!nodeMap.containsKey(endNode.getName())) {
                nodeMap.put(endNode.getName(), endNode);
            }
        }
        Collection<PersonDto> values = nodeMap.values();
        List<PersonDto> collect = values.stream().collect(Collectors.toList());
        int length = collect.size();
        PersonDto personDto = collect.get(length-1);
        personDto.setDegree(null);
        map.put("nodes", collect);
        return map;
    }
}
