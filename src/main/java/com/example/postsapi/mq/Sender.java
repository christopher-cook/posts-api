package com.example.postsapi.mq;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.core.RabbitTemplate;


@Component
public class Sender {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    Queue queue;

    public void send(String message) {
        System.out.println("deleting comments");
        rabbitTemplate.convertAndSend(queue.getName(), message);
    }
}
