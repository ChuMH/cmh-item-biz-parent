package com.cmh.item.biz.msg.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;

/*
 *测试
 */
@Slf4j
@Service
public class FooMessageListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        String messageBody = new String(message.getBody());
        log.info("===>接收到消息 msg{}  ",messageBody);
}
}
