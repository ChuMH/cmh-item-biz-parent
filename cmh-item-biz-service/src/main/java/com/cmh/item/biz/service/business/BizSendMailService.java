package com.cmh.item.biz.service.business;

import com.cmh.item.biz.sdk.dto.register.Mail;
import com.cmh.project.basis.base.ResultBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author：初明昊
 * @data：2020/05/01
 * @description：发送邮件服务
 */
@Slf4j
@Service
public class BizSendMailService {
    @Value("${spring.mail.from}")
    private String from;

    @Resource
    private JavaMailSender mailSender;

    @Resource
    private ThreadPoolTaskExecutor bizExecutor;

    public ResultBuilder<Boolean> sendMail(Mail mail) {
        String to = mail.getTo();// 目标邮箱
        String title = mail.getTitle();// 邮件标题
        String content = mail.getContent();// 邮件正文

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(title);
        message.setText(content);

        try {
            bizExecutor.submit(() -> mailSender.send(message));
            log.info("==>SendMailServiceImpl.sendMail邮件发送成功");
            return ResultBuilder.success(true);
        } catch (MailException e) {
            log.error("SendMailServiceImpl.sendMail邮件发送失败, to: {}, title: {}", to, title, e);
            return ResultBuilder.failure(false);
        }
    }
}
