package com.cmh.item.biz.web.controller;

import com.cmh.item.biz.dao.db.entity.User;
import com.cmh.item.biz.dao.db.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author：初明昊
 * @data：2020/05/01
 * @description：测试
 */
@RestController
public class HelloController {

    @Autowired
    private UserService userService;

    @RequestMapping("/test")
    public String test()
    {
        HashMap map = new HashMap();
        map.put("zhangsan","123");
        return "helloworld!";
    }

    @RequestMapping("insert")
    public String inertTest(){
        User user = new User();
        user.setId(0L);
        user.setUserName("chuminghao1");
        user.setPassword("123456");
        user.setEmail("chuminghao566@163.com");
        userService.insert(user);
        return "success!!!";
    }
}
