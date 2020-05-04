package com.cmh.item.biz.service.mq.consumer;

import com.cmh.item.biz.comm.enums.MailEnum;
import com.cmh.item.biz.msg.config.RabbitConfig;
import com.cmh.item.biz.sdk.dto.register.Mail;
import com.cmh.item.biz.sdk.service.mail.MailService;
import com.cmh.item.biz.service.mq.BaseConsumer;
import com.cmh.item.biz.service.util.MailUtil;
import com.cmh.item.biz.service.util.converter.MessageHelper;
import com.cmh.project.basis.base.ResultBuilder;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@Slf4j
public class MailConsumer implements BaseConsumer {

    private static final Mail EMPTY_MAIL = new Mail();

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private MailService mailService;

    @RabbitListener(queues = RabbitConfig.MAIL_QUEUE_NAME)
    @ConditionalOnExpression("${mq.consumer.enabled}==1&&${mq.rabbitmq.consumer.enabled:true}")
    public void consume(Message message, Channel channel) throws IOException {
        Mail mail = MessageHelper.msgToObj(message, Mail.class);
        mail = Optional.ofNullable(mail).orElseGet(EMPTY_MAIL);
        log.info("===>收到消息: {}", mail.toString());
        mail.setTo(mail.getTo());
        mail.setTitle(MailEnum.TITLE_REGISTER_SUCCESS.getMessage());
        mail.setContent(MailEnum.CONTENT_REGISTER_SUCCESS.getMessage());
        ResultBuilder<Boolean> success = mailService.sendMail(mail);
        log.info("===>发送成功，success={}",success);
        MessageProperties properties = message.getMessageProperties();
        long deliveryTag = properties.getDeliveryTag();
        if (success.getData()) {
            //消费确认
            channel.basicAck(deliveryTag,false);
        }else{
            //确认失败 第三个参数true重新入队列false丢弃，false不接受先前未ack的消息false多消息
            channel.basicNack(deliveryTag, false, true);
            throw new RuntimeException("send mail error");
        }

    }

}
