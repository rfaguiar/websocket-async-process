package com.asyncapp.backend1.application.queue;

import com.asyncapp.backend1.application.queue.model.TokenMessage;
import com.asyncapp.backend1.domain.mapper.TokenMapper;
import com.asyncapp.backend1.domain.service.TokenCallbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static org.springframework.web.util.HtmlUtils.htmlEscape;

@Slf4j
@Component
public class RabbitMqListener {

    private final TokenMapper tokenMapper;
    private final TokenCallbackService tokenCallbackService;

    public RabbitMqListener(TokenMapper tokenMapper,
                            TokenCallbackService tokenCallbackService) {
        this.tokenMapper = tokenMapper;
        this.tokenCallbackService = tokenCallbackService;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue_response}")
    public void consumer(TokenMessage message) {
        log.info("Received Response Message {}", htmlEscape(message.toString()));
        var tokenDto = tokenMapper.toTokenDto(message);
        tokenCallbackService.callbackToUser(tokenDto);
    }
}
