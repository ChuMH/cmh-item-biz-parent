package com.cmh.item.biz.service.mq.consumer;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LogConsumer{

    /*@RabbitListener(queues = RabbitConfig.LOG_QUEUE_NAME)
    public void consume(Message message, Channel channel) throws IOException {
        OpLog opLog = MessageHelper.msgToObj(message, OpLog.class);
        log.info("===>收到Log消息: {}", opLog.toString());
        MessageProperties properties = message.getMessageProperties();
        long deliveryTag = properties.getDeliveryTag();
        //消费确认
        channel.basicAck(deliveryTag,false);
    }*/
}
