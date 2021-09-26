package com.asyncapp.backend1.infrastructure.queue;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class RabbitmqProperties {

    private String exchangeRequest;
    private String queueRequest;
    private String exchangeResponse;
    private String queueResponse;
    private String routingkey;

}
