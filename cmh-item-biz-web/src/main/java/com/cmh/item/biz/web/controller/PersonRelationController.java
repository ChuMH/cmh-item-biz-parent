
package com.cmh.item.biz.web.controller;

import com.cmh.item.biz.sdk.dto.neo4j.FindPathDto;
import com.cmh.item.biz.sdk.dto.neo4j.PersonShortestPathDto;
import com.cmh.item.biz.sdk.dto.neo4j.TopNDto;
import com.cmh.item.biz.sdk.service.neo4j.NeoPersonQueryService;
import com.cmh.project.basis.base.ResultBuilder;
import com.cmh.project.basis.utils.json.FastJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author：初明昊
 * @data：2020/05/01
 * @description：基于大数据用户网络关系应用
 */
@Slf4j
@RestController
@RequestMapping("sys")
public class PersonRelationController {

    @Resource
    private NeoPersonQueryService neoPersonQueryService;

    /**
     * 查询出两用户最短路径
     * @param request
     * @return
     *
     */
    @RequestMapping(value = "shortestPath", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    public ResultBuilder queryShortestPath(@RequestBody PersonShortestPathDto request){
        log.info("===>PersonRelationController.queryShortestPath,request={}",FastJsonUtil.obj2json(request));
        return ResultBuilder.success(neoPersonQueryService.queryShortestPath(request).getData());
    }

    /**
     * 查询出该用户的三级辐射图
     * @param request
     * @return
     */
    @RequestMapping(value = "findPath", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    public ResultBuilder queryTargetRadiationByLevel(@RequestBody FindPathDto request){
        log.info("===>PersonRelationController.queryTargetRadiationByLevel,request={}",request);
        return ResultBuilder.success(neoPersonQueryService.queryTargetRadiationByLevel(request.getUserId()).getData());
    }


    /**
     * TOP-N方式求当前图形数据库中关联关系最多和热度最高的用户
     * @param request
     * @return
     */
    @RequestMapping(value = "topN", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    public ResultBuilder queryTop(@RequestBody TopNDto request){
        Integer max = request.getMax();
        log.info("===>PersonRelationController.queryTop,max={}",max);
        if(max==null ||max<0 || max>10){
            max = 10;
        }
        return ResultBuilder.success(neoPersonQueryService.queryTop(max).getData());
    }
}
