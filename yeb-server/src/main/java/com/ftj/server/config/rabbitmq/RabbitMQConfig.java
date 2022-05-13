package com.ftj.server.config.rabbitmq;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ftj.server.constants.MailConstants;
import com.ftj.server.pojo.MailLog;
import com.ftj.server.service.IMailLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by fengtj on 2022/5/13 8:03
 */
@Slf4j
@Configuration
public class RabbitMQConfig {

    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;
    @Autowired
    private IMailLogService mailLogService;

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);

        //消息确认回调，确认消息是否到达broker  data消息唯一标识 ack 确认结果 cause失败原因
        rabbitTemplate.setConfirmCallback((data, ack, cause) -> {
            if (ack) {
                log.info("{}====>消息发送成功", data.getId());
                mailLogService.update(new UpdateWrapper<MailLog>().set("status", 1).eq("msgId", data.getId()));
            } else {
                log.error("{}====>消息发送失败", data.getId());
            }
        });

        //消息失败回调 比如router不到queue时回调 msg消息主题 repCode响应码 repText响应描述 exchange交换机 routingKey路由键
        rabbitTemplate.setReturnCallback((msg, repCode, repText, exchange, routingKey) -> {
            log.error("{}====>消息发送到queue失败", msg.getBody());
        });
        return rabbitTemplate;
    }

    @Bean
    public Queue queue() {
        return new Queue(MailConstants.MAIL_QUEUE_NAME);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(MailConstants.MAIL_EXCHANGE_NAME);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(directExchange()).with(MailConstants.MAIL_ROUTING_KEY_NAME);
    }
}
