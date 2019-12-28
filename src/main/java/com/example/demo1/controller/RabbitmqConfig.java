package com.example.demo1.controller;



import com.example.demo1.rabbitmq.UserOrderListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;


@Configuration
class RabbitmqConfig {
    private static final Logger log= LoggerFactory.getLogger(Logger.class);
    @Autowired
    private Environment env;

    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Autowired
    private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;

    /**
     * 单一消费者
     *
     * @return
     */
    @Bean(name = "singleListenerContainer")
    public SimpleRabbitListenerContainerFactory listenerContainer() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(1);
        factory.setPrefetchCount(1);
        factory.setTxSize(1);
        //设置当rabbitmq收到nack/reject确认信息时的处理方式，设为true，扔回queue头部，设为false，丢弃。
        //factory.setDefaultRequeueRejected(true);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return factory;
    }

    /**
     * 多个消费者
     *
     * @return
     */
    @Bean(name = "multiListenerContainer")
    public SimpleRabbitListenerContainerFactory multiListenerContainer() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factoryConfigurer.configure(factory, connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setAcknowledgeMode(AcknowledgeMode.NONE);
        factory.setConcurrentConsumers(env.getProperty("spring.rabbitmq.listener.concurrency", int.class));
        factory.setMaxConcurrentConsumers(env.getProperty("spring.rabbitmq.listener.max-concurrency", int.class));
        factory.setPrefetchCount(env.getProperty("spring.rabbitmq.listener.prefetch", int.class));


        return factory;
    }

    @Bean
    @Scope("prototype")
    public RabbitTemplate rabbitTemplate() {
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        return rabbitTemplate;
    }

    public Queue logUserQueue() {
        return new Queue(env.getProperty("log.user.queue.name"), true);
    }

    @Bean
    public DirectExchange logUserExchange() {
        return new DirectExchange(env.getProperty("log.user.exchange.name"), true, false);
    }

    public Binding logUserBingding() {
        return BindingBuilder.bind(logUserQueue()).to(logUserExchange()).with(env.getProperty("log.user.routing.key.name"));
    }


    /**
     * 消息一
     *
     * @return
     */
    public Queue logUserQueue1() {
        return new Queue(env.getProperty("log.user.queue.name1"), true);
    }

    @Bean
    public DirectExchange logUserExchange1() {
        return new DirectExchange(env.getProperty("log.user.exchange.name"), true, false);
    }

    public Binding logUserBingding1() {
        return BindingBuilder.bind(logUserQueue1()).to(logUserExchange1()).with(env.getProperty("log.user.routing.key.name1"));
    }

    /**
     * 消息二
     *
     * @return
     */
    public Queue logUserQueue2() {
        return new Queue(env.getProperty("log.user.queue.name2"), true);
    }

    @Bean
    public DirectExchange logUserExchange2() {
        return new DirectExchange(env.getProperty("log.user.exchange.name"), true, false);
    }

    public Binding logUserBingding2() {
        return BindingBuilder.bind(logUserQueue2()).to(logUserExchange2()).with(env.getProperty("log.user.routing.key.name2"));
    }
    //TODO：用户商城抢单实战

    @Bean(name = "userOrderQueue")
    public Queue userOrderQueue(){
        return new Queue(env.getProperty("user.order.queue.name"),true);
    }

    @Bean
    public TopicExchange userOrderExchange(){
        return new TopicExchange(env.getProperty("user.order.exchange.name"),true,false);
    }

    @Bean
    public Binding userOrderBinding(){
        return BindingBuilder.bind(userOrderQueue()).to(userOrderExchange()).with(env.getProperty("user.order.routing.key.name"));
    }

    @Autowired
    private UserOrderListener userOrderListener;

    @Bean
    public SimpleMessageListenerContainer listenerContainerUserOrder(@Qualifier("userOrderQueue") Queue userOrderQueue){
        SimpleMessageListenerContainer container=new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setMessageConverter(new Jackson2JsonMessageConverter());

        //TODO：并发配置
        container.setConcurrentConsumers(env.getProperty("spring.rabbitmq.listener.concurrency",Integer.class));
        container.setMaxConcurrentConsumers(env.getProperty("spring.rabbitmq.listener.max-concurrency",Integer.class));
        container.setPrefetchCount(env.getProperty("spring.rabbitmq.listener.prefetch",Integer.class));

        //TODO:消息确认机制
        container.setQueues(userOrderQueue);
        container.setMessageListener(userOrderListener);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);

        return container;
    }





    //创建死信队列
    @Bean
    public Queue simpleDeadQueue(){
        Map<String, Object> args=new HashMap();

        args.put("x-dead-letter-exchange", env.getProperty("simple.dead.exchange.name"));
        args.put("x-dead-letter-routing-key", env.getProperty("simple.dead.routing.key.name"));
        args.put("x-message-ttl", 10000);

        return new Queue(env.getProperty("simple.dead.queue.name"),true,false,false,args);
    }

    //绑定死信队列-面向生产端
    @Bean
    public TopicExchange simpleDeadExchange(){
        return new TopicExchange(env.getProperty("simple.produce.exchange.name"),true,false);
    }

    @Bean
    public Binding simpleDeadBinding(){
        return BindingBuilder.bind(simpleDeadQueue()).to(simpleDeadExchange()).with(env.getProperty("simple.produce.routing.key.name"));
    }















    /**
     * 死信队列 交换机标识符
     */
    private static final String DEAD_LETTER_QUEUE_KEY = "x-dead-letter-exchange";

    /**
     * 死信队列交换机绑定键标识符
     */
    private static final String DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";


    /**
     * 死信队列跟交换机类型没有关系 不一定为directExchange 不影响该类型交换机的特性.
     */
    @Bean("deadLetterExchange")
    public DirectExchange deadLetterExchange() {
        // return (DirectExchange)
        // ExchangeBuilder.directExchange("DL_EXCHANGE").durable(true).build();
        return new DirectExchange("DL_EXCHANGE", true, false);
    }

    /**
     * 声明一个死信队列. x-dead-letter-exchange 对应 死信交换机 x-dead-letter-routing-key 对应
     * 死信队列
     */
    @Bean("deadLetterQueue")
    public Queue deadLetterQueue() {
        Map<String, Object> args = new HashMap<>(2);
        // x-dead-letter-exchange 声明 死信交换机
        args.put(DEAD_LETTER_QUEUE_KEY, "DL_EXCHANGE");
        // x-dead-letter-routing-key 声明 死信路由键
        args.put(DEAD_LETTER_ROUTING_KEY, "KEY_R");
        return QueueBuilder.durable("DL_QUEUE").withArguments(args).build();
    }

    /**
     * 定义死信队列转发队列.
     *
     * @return the queue
     */
    @Bean("redirectQueue")
    public Queue redirectQueue() {
        return QueueBuilder.durable("REDIRECT_QUEUE").build();
    }

    /**
     * 死信路由通过 DL_KEY 绑定键绑定到死信队列上.
     *
     * @return the binding
     */
    @Bean
    public Binding deadLetterBinding() {
        return new Binding("DL_QUEUE", Binding.DestinationType.QUEUE, "DL_EXCHANGE", "DL_KEY", null);
    }

    /**
     * 死信路由通过 KEY_R 绑定键绑定到死信队列上.
     *
     * @return the binding
     */
    @Bean
    public Binding redirectBinding() {
        return new Binding("REDIRECT_QUEUE", Binding.DestinationType.QUEUE, "DL_EXCHANGE", "KEY_R", null);
    }


















}
