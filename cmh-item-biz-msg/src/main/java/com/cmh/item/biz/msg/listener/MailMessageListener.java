package com.cmh.item.biz.msg.listener;

import com.cmh.item.biz.sdk.dto.register.Mail;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/*
 *测试
 */
@Slf4j
public class MailMessageListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        String body = new String(message.getBody());
        ObjectMapper mapper = new ObjectMapper();
        try{
            Mail mail = mapper.readValue(body,Mail.class);
            log.info("===>接收到邮件消息："+mail);
            sendEmail(mail);
        } catch (Exception e) {
            log.error("系统内部错误");
        }
    }
    public void sendEmail(Mail mail){
        //调用JavaMail API 发送邮件

    }
}
