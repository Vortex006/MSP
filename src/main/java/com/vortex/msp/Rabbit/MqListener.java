package com.vortex.msp.Rabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MqListener {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

//    @RabbitListener(queues = "android.queue")
//    public void listenSimpleQueue(String msg) {
//        logger.info("消费者1 收到了android.queue的消息：{}", msg);
//    }
//
//    @RabbitListener(queues = "work.queue")
//    public void listenSimpleQueue2(String msg) {
//        logger.info("消费者2 收到了work.queue的消息：{}", msg);
//    }
}
