package com.cmh.item.biz.service.business;

import com.cmh.item.biz.msg.config.RabbitConfig;
import com.cmh.item.biz.sdk.dto.log.MsgLog;
import com.cmh.item.biz.sdk.dto.register.Mail;
import com.cmh.item.biz.sdk.dto.register.RegisterUserInfo;
import com.cmh.item.biz.service.util.JsonUtil;
import com.cmh.item.biz.service.util.RandomUtil;
import com.cmh.item.biz.service.util.converter.MessageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author：
 * @data：
 * @description：
 */
@Slf4j
@Service
public class BizUserRegisterService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public Boolean register(RegisterUserInfo user) {
        //校验用户填写的信息是否完整

        //将用户及相关信息保存到数据库
        log.info("===>保存到数据库成功");
        return true;
    }

    public Boolean produce(RegisterUserInfo user) {
        Mail mail = new Mail();
        //生成随机邮件id，入库保存使用，消息使用
        String msgId = RandomUtil.UUID32();
        mail.setMsgId(msgId);
        mail.setTo(user.getMail());
        String msg = JsonUtil.objToStr(mail);
        MsgLog msgLog = new MsgLog(msgId,msg,RabbitConfig.MAIL_EXCHANGE_NAME,RabbitConfig.MAIL_ROUTING_KEY_NAME);
        //消息入库

        CorrelationData correlationData = new CorrelationData(msgId);
        rabbitTemplate.convertAndSend(RabbitConfig.MAIL_EXCHANGE_NAME,RabbitConfig.MAIL_ROUTING_KEY_NAME,
                MessageHelper.objToMsg(mail),correlationData);
        log.info("===>produce消息发送成功");
        return true;
    }
}
