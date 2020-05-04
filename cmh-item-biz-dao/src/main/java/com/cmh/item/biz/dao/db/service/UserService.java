package com.cmh.item.biz.dao.db.service;

import com.cmh.item.biz.dao.db.entity.User;
import com.cmh.item.biz.dao.db.mapper.UserMapper;
import com.cmh.project.basis.utils.json.FastJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author：初明昊
 * @data：2020/04/24
 * @description：数据库服务
 */
@Slf4j
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 新增用户
     */
    public int insert(User user){
        try {
            int res = userMapper.insert(user);
            return res;
        }catch (Exception e){
            log.error("===>UserService.insert,error.request={}",FastJsonUtil.obj2json(user));
        }
        return 0;
    }

    /**
     * 查询用户
     */
    public User select(User user){
        try {
            User res = userMapper.select(user);
            return res;
        }catch (Exception e){
            log.error("===>UserService.select.error.request={}",FastJsonUtil.obj2json(user));
        }
        return new User();
    }
}
