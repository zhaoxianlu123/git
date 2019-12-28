//package com.example.demo1.controller;
//
//import org.apache.log4j.Logger;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//
//public abstract class SmartMQFailedCallBack implements RabbitTemplate.ReturnCallback {
//    protected final Logger logger = Logger.getLogger("bo");
//    /**
//     * 确认消息是否到达broker服务器，也就是只确认是否正确到达queue中即可，只要正确的到达queue中，broker即可确认该消息返回给客户端ack。
//     */
//    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
//        if (null != message) {
//            logger.error( "消息发送到MQ失败,内容["+message.getBody()+"]");
//            executeFailedMessage(message.getBody());
//        }else {
//            logger.error("消息发送到MQ失败,消息内容为null");
//        }
//    }
//
//    public abstract void executeFailedMessage(Object message);
//}