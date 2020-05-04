package com.cmh.item.biz.sdk.service.mail;

import com.cmh.item.biz.sdk.dto.register.Mail;
import com.cmh.project.basis.base.ResultBuilder;

/**
 * @author：初明昊
 * @data：2020/05/01
 * @description：邮件发送接口
 */
public interface MailService {
    ResultBuilder<Boolean> sendMail(Mail mail);
}
