package com.asyncapp.backend1.infrastructure.queue;

import com.asyncapp.backend1.infrastructure.request.QueueRequest;
import com.asyncapp.backend1.infrastructure.response.QueueResponse;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {

    private final RabbitmqProperties configRabbitmqProperties;

    public RabbitMqConfiguration(RabbitmqProperties configRabbitmqProperties) {
        this.configRabbitmqProperties = configRabbitmqProperties;
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    //producer configs
    @Bean
    @QueueRequest
    public Exchange declareExchangeRequest() {
        return ExchangeBuilder.directExchange(configRabbitmqProperties.getExchangeRequest())
                .durable(true)
                .build();
    }

    @Bean
    @QueueResponse
    public Exchange declareExchangeResponse() {
        return ExchangeBuilder.directExchange(configRabbitmqProperties.getExchangeResponse())
                .durable(true)
                .build();
    }

    //consumer configs
    @Bean
    @QueueRequest
    public Queue declareQueueRequest() {
        return QueueBuilder.durable(configRabbitmqProperties.getQueueRequest())
                .build();
    }

    @Bean
    @QueueResponse
    public Queue declareQueueResponse() {
        return QueueBuilder.durable(configRabbitmqProperties.getQueueResponse())
                .build();
    }

    @Bean
    public Binding declareBindingRequest(@QueueRequest Exchange exchange,
                                         @QueueRequest Queue queue) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with(configRabbitmqProperties.getRoutingkey())
                .noargs();
    }

    @Bean
    public Binding declareBindingResponse(@QueueResponse Exchange exchange,
                                         @QueueResponse Queue queue) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with(configRabbitmqProperties.getRoutingkey())
                .noargs();
    }
}
