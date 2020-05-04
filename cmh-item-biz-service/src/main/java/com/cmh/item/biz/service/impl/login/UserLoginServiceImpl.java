package com.cmh.item.biz.service.impl.login;

import com.cmh.item.biz.dao.db.entity.User;
import com.cmh.item.biz.sdk.service.login.LoginService;
import com.cmh.item.biz.service.business.BizUserLoginService;
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
public class UserLoginServiceImpl implements LoginService {

    @Autowired
    private BizUserLoginService bizUserLoginService;

    @Override
    public ResultBuilder login(User user) {
        return bizUserLoginService.login(user);
    }
}
