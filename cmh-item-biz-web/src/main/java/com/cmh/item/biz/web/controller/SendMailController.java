package com.cmh.item.biz.web.controller;

import com.cmh.item.biz.sdk.dto.register.Mail;
import com.cmh.item.biz.sdk.service.mail.MailService;
import com.cmh.project.basis.base.ResultBuilder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author：
 * @data：
 * @description：
 */
@RestController
@RequestMapping("/send")
public class SendMailController {

    @Resource
    private MailService mailService;

    @RequestMapping(value = "/mail", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    public ResultBuilder<Boolean> sendMail(@RequestBody Mail mail){
        return mailService.sendMail(mail);
    }
}
