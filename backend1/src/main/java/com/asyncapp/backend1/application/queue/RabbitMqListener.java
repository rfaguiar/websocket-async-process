package com.asyncapp.backend1.application.queue;

import com.asyncapp.backend1.application.queue.model.TokenMessage;
import com.asyncapp.backend1.domain.mapper.TokenMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static org.springframework.web.util.HtmlUtils.htmlEscape;

@Slf4j
@Component
public class RabbitMqListener {

    private final TokenMapper tokenMapper;

    public RabbitMqListener(TokenMapper tokenMapper) {
        this.tokenMapper = tokenMapper;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue_response}")
    public void consumer(TokenMessage message) {
        log.info("Received Response Message {}", htmlEscape(message.toString()));
        var tokenDto = tokenMapper.toTokenDto(message);
    }
}
