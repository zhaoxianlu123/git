package com.example.demo1.controller;

import com.example.demo1.response.BaseResponse;
import com.example.demo1.response.StatusCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);


    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Environment env;

    //消息确认机制，如果消息已经发出，但是rabbitmq并没有回应或者是拒绝接收消息了呢？就可以通过回调函数的方式将原因打印出来
    RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {

        public void confirm(CorrelationData correlationData, boolean isack, String cause) {
            System.out.println("本次消息的唯一标识是:" + correlationData);
            System.out.println("是否存在消息拒绝接收？" + isack);
            if (isack == false) {
                System.out.println("消息拒绝接收的原因是:" + cause);
            } else {
                System.out.println("消息发送成功");
            }
        }
    };

    //有关消息被退回来的具体描述消息
    RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {

        @Override
        public void returnedMessage(Message message,
                                    int replyCode,
                                    String desc,
                                    String exchangeName,
                                    String routeKey) {
            System.out.println("err code :" + replyCode);
            System.out.println("错误消息的描述 :" + desc);
            System.out.println("错误的交换机是 :" + exchangeName);
            System.out.println("错误的路右键是 :" + routeKey);

        }
    };


    @RequestMapping(value = "/mq1")
    public Object mq1() {
        BaseResponse response = new BaseResponse(StatusCode.Success);
        CorrelationData correlationId = null;
        try {
            //TODO：执行登录逻辑
            if ("ddd" != null) {
                //TODO：异步写用户日志
                try {
                    correlationId = new CorrelationData(UUID.randomUUID().toString());
                    rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                    String exchange = env.getProperty("log.user.exchange.name");
                    String routing = env.getProperty("log.user.routing.key.name1");
                    rabbitTemplate.setExchange(exchange);
                    rabbitTemplate.setRoutingKey(routing);

                    Message message = MessageBuilder.withBody(objectMapper.writeValueAsBytes("123456")).setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
                    message.getMessageProperties().setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, MessageProperties.CONTENT_TYPE_JSON);
                    //rabbitTemplate.convertAndSend(message);
                    rabbitTemplate.setMandatory(true);
                    rabbitTemplate.setConfirmCallback(confirmCallback);
                    rabbitTemplate.setReturnCallback(returnCallback);
                    rabbitTemplate.convertSendAndReceive(message, correlationId);

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("2");
                }

                //TODO：塞权限数据-资源数据-视野数据
            } else {
                response = new BaseResponse(StatusCode.Fail);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("3");
        }
        return "1";
    }

    @RequestMapping(value = "/mq2")
    public String mq2() {
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            //TODO：执行登录逻辑
            if ("ddd" != null) {
                //TODO：异步写用户日志
                try {
                    rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                    rabbitTemplate.setExchange(env.getProperty("log.user.exchange.name"));
                    rabbitTemplate.setRoutingKey(env.getProperty("log.user.routing.key.name2"));

                    Message message = MessageBuilder.withBody(objectMapper.writeValueAsBytes("654321")).setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
                    message.getMessageProperties().setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, MessageProperties.CONTENT_TYPE_JSON);
                    rabbitTemplate.convertAndSend(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //TODO：塞权限数据-资源数据-视野数据
            } else {
                response = new BaseResponse(StatusCode.Fail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "2";
    }

    @RequestMapping(value = "/mq3")
    public String mq3() {
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            //TODO：执行登录逻辑
            if ("ddd" != null) {
                //TODO：异步写用户日志
                try {
                    rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                    rabbitTemplate.setExchange(env.getProperty("simple.produce.exchange.name"));
                    rabbitTemplate.setRoutingKey(env.getProperty("simple.produce.routing.key.name"));

                    Message message = MessageBuilder.withBody(objectMapper.writeValueAsBytes("654321")).setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
                    message.getMessageProperties().setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, MessageProperties.CONTENT_TYPE_JSON);
                    rabbitTemplate.convertAndSend(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //TODO：塞权限数据-资源数据-视野数据
            } else {
                response = new BaseResponse(StatusCode.Fail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "2";
    }


}

