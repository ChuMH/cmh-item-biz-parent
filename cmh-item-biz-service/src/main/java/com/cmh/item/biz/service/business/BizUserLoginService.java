package com.cmh.item.biz.service.business;

import com.cmh.item.biz.dao.db.entity.User;
import com.cmh.item.biz.dao.db.service.UserService;
import com.cmh.project.basis.base.ResultBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author：
 * @data：
 * @description：
 */
@Slf4j
@Service
public class BizUserLoginService {

    @Autowired
    private UserService userService;

    public ResultBuilder<User> login(User user){
        User response = userService.select(user);
        if(response != null){
            return ResultBuilder.success("登录成功",response);
        }else {
            return ResultBuilder.failure("您的账号不存在",user);
        }
    }
}
