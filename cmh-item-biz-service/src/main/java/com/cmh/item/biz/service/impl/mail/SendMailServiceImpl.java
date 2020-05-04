package com.cmh.item.biz.service.impl.mail;

import com.cmh.item.biz.sdk.dto.register.Mail;
import com.cmh.item.biz.sdk.service.mail.MailService;
import com.cmh.item.biz.service.business.BizSendMailService;
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
public class SendMailServiceImpl implements MailService {

    @Autowired
    private BizSendMailService bizSendMailService;

    @Override
    public ResultBuilder<Boolean> sendMail(Mail mail) {
        return bizSendMailService.sendMail(mail);
    }
}
