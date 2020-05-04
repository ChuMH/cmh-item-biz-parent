package com.cmh.item.biz.service.impl.register;

import com.cmh.item.biz.sdk.dto.register.RegisterUserInfo;
import com.cmh.item.biz.sdk.service.register.RegisterService;
import com.cmh.item.biz.service.business.BizUserRegisterService;
import com.cmh.project.basis.base.ResultBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class RegisterServiceImpl implements RegisterService {

    @Resource
    private BizUserRegisterService bizUserRegister;

    @Override
    public ResultBuilder<Boolean> register(RegisterUserInfo user) {
        return ResultBuilder.success(bizUserRegister.register(user));
    }

    @Override
    public ResultBuilder<Boolean> produce(RegisterUserInfo user) {
        return ResultBuilder.success(bizUserRegister.produce(user));
    }
}
