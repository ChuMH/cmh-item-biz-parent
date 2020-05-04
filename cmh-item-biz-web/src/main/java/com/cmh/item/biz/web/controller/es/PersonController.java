package com.cmh.item.biz.web.controller.es;

import com.cmh.item.biz.dao.es.entity.Person;
import com.cmh.item.biz.sdk.service.es.PersonService;
import com.cmh.project.basis.base.ResultBuilder;
import com.google.common.collect.Maps;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/es")
public class PersonController {

    @Resource
    private PersonService personService;

    @RequestMapping(value = "/add/person",method = {RequestMethod.GET,RequestMethod.POST},produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultBuilder<Boolean> add(@RequestBody Person person){
        Map<String,Object> map = beanToMap(person);
        personService.add(map);
        return ResultBuilder.success();
    }

    public  <T> Map<String, Object> beanToMap(Object bean) {
        Map<String, Object> map = Maps.newHashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key+"", beanMap.get(key));
            }
        }
        return map;
    }
}
