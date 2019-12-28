package com.example.demo1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class CommonMqListener {


    @Autowired
    private ObjectMapper objectMapper;


    /**
     * 监听消费用户日志
     *
     * @param mess
     */
    @RabbitListener(queues = "${log.user.queue.name1}")
    public String consumeUserLogQueue1(@Payload byte[] mess) throws Exception {

        try {
            String result = new String(mess, "UTF-8");
            System.out.println("1");
            int i = 1 / 0;
            //TODO：记录日志入数据表
            //手动应答
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("DDDDDDDD");
        }
        return "00000";
    }

    /**
     * 监听消费用户日志
     *
     * @param message
     */
    @RabbitListener(queues = "${log.user.queue.name2}")
    public void consumeUserLogQueue2(@Payload byte[] message) {
        try {
            String result = new String(message, "UTF-8");
            System.out.println(result);
            //TODO：记录日志入数据表
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 监听消费用户日志
     *
     * @param message
     */
    @RabbitListener(queues = "${simple.dead.queue.name}")
    public void consumeUserLogQueue3(@Payload byte[] message) {
        try {
            String result = new String(message, "UTF-8");
            System.out.println(result);
            //TODO：记录日志入数据表
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}