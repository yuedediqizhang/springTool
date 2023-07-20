package com.example.processexeserver;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.example.processexeserver.RabbitConstant.*;

@Configuration
public class RabbitConfig {


    @Value("${rabbitmq.address}")
    private String mqAddress;
    @Value("${rabbitmq.username}")
    private String mqUsername;
    @Value("${rabbitmq.password}")
    private String mqPassword;

    @Bean
    public Queue requestProcessExeQueue() {
        return new Queue(REQUEST_PROCESS_EXE_QUEUE, true);
    }

    @Bean
    public DirectExchange requestProcessExeExchange() {
        return new DirectExchange(REQUEST_PROCESS_EXE_EXCHANGE, true, false);
    }

    @Bean
    public Binding requestProcessExeBinding() {
        return BindingBuilder.bind(requestProcessExeQueue()).to(requestProcessExeExchange()).with(REQUEST_PROCESS_EXE_RK);
    }

    @Bean
    public Queue receiveProcessExeQueue() {
        return new Queue(RECEIVE_PROCESS_EXE_QUEUE, true);
    }

    @Bean
    public DirectExchange receiveProcessExeExchange() {
        return new DirectExchange(RECEIVE_PROCESS_EXE_EXCHANGE, true, false);
    }

    @Bean
    public Binding receiveProcessExeBinding() {
        return BindingBuilder.bind(receiveProcessExeQueue()).to(receiveProcessExeExchange()).with(RECEIVE_PROCESS_EXE_RK);
    }
    
    @Bean
    public MessageConverter jsonbMessageConverter() {
        // messageConverter 用于序列化消息体
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(mqAddress);
        connectionFactory.setUsername(mqUsername);
        connectionFactory.setPassword(mqPassword);
        connectionFactory.setVirtualHost("/");
        return connectionFactory;
    }


    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        // simpleRabbitListenerContainerFactory 用于接收消息
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        // 设置连接工厂
        factory.setConnectionFactory(connectionFactory());
        // 设置最大消费者数量
        factory.setMaxConcurrentConsumers(5);
        // 设置消费者数量
        factory.setConcurrentConsumers(3);
        // 设置预取数量
        factory.setPrefetchCount(1);
        // 设置消息转换器
        factory.setMessageConverter(jsonbMessageConverter());
        return factory;
    }
}