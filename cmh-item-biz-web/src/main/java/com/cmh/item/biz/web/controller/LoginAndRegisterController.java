package com.cmh.item.biz.web.controller;

import com.cmh.item.biz.dao.db.entity.User;
import com.cmh.item.biz.sdk.dto.register.RegisterUserInfo;
import com.cmh.item.biz.sdk.service.login.LoginService;
import com.cmh.item.biz.sdk.service.register.RegisterService;
import com.cmh.project.basis.base.ResultBuilder;
import com.cmh.project.basis.base.constant.SysResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author：初明昊
 * @data：2020/05/01
 * @description：登录注册入口
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class LoginAndRegisterController {

    @Resource
    private RegisterService registerService;

    @Resource
    private LoginService loginService;

    @Resource
    private ThreadPoolTaskExecutor bizExecutor;

    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    public ResultBuilder<Boolean> login(@RequestBody User user){
        return loginService.login(user);
    }

    @RequestMapping(value = "/register", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    public ResultBuilder<Boolean> register(@RequestBody RegisterUserInfo user) {
        try {
            //校验用户信息是否完整
            if (user == null) {
                return ResultBuilder.failure(SysResultCode.SYS_BAD_REQUEST.getMsg(), false);
            }
            //调用注册用户接口
            ResultBuilder<Boolean> response = registerService.register(user);
            if (response.getData()) {
                bizExecutor.submit(()->{
                    //发送邮件消息(生产)
                    registerService.produce(user);
                });
            }
            return ResultBuilder.success(SysResultCode.SUCCESS.getMsg(), true);
        } catch (Exception e) {
            return ResultBuilder.failure(SysResultCode.SYS_SERVER_ERROR.getMsg(),false);
        }
    }
}
