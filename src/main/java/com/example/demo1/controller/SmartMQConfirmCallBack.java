//package com.example.demo1.controller;
//
//import org.apache.log4j.Logger;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.rabbit.support.CorrelationData;
//
//
//public class SmartMQConfirmCallBack implements RabbitTemplate.ConfirmCallback {
//    protected final Logger logger = Logger.getLogger("bo");
//    /**
//     * 确认消息是否到达broker服务器，也就是只确认是否正确到达exchange中即可，只要正确的到达exchange中，broker即可确认该消息返回给客户端ack。
//     *
//     */
//    @Override
//    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
//        if (ack) {
//            logger.info("消息成功消费，消息ID["+correlationData.getId()+"]");
//        } else {
//            logger.error( "消息失败消费，消息ID["+correlationData.getId()+"]");
//        }
//        executeCallBack(correlationData.getId(),ack);
//
//    }
//
//    private void executeCallBack(String id, boolean ack) {
//    }
//
//
//}
