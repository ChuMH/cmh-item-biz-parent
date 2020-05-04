package com.cmh.item.biz.sdk.service.register;

import com.cmh.item.biz.sdk.dto.register.RegisterUserInfo;
import com.cmh.project.basis.base.ResultBuilder;


/*
 * 作者：初明昊
 * 时间：2019/12/8 15:04
 * 描述：注册相关服务
 */
public interface RegisterService {
    /*
     * 注册用户到库
     */
    ResultBuilder<Boolean> register(RegisterUserInfo user);
    /*
     * 发送注册成功通知
     */
    ResultBuilder<Boolean> produce(RegisterUserInfo userInfo);

}
