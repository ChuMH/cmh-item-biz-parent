package com.cmh.item.biz.sdk.service.login;

import com.cmh.item.biz.dao.db.entity.User;
import com.cmh.project.basis.base.ResultBuilder;

/**
 * @author：
 * @data：
 * @description：
 */
public interface LoginService {

    ResultBuilder login(User user);
}
