package com.tensquare.rebbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "itcast")
public class Customer01 {

    @RabbitHandler
    public void getMsg(String msg) {
        System.out.println("直接模式消费:" +msg);
    }
}
