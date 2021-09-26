package com.asyncapp.backend2.application.queue;

import com.asyncapp.backend2.application.model.ProcessMessage;
import com.asyncapp.backend2.domain.mapper.TokenMapper;
import com.asyncapp.backend2.domain.service.TokenProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static org.springframework.web.util.HtmlUtils.htmlEscape;

@Slf4j
@Component
public class RabbitMqListener {

    private final TokenMapper tokenMapper;
    private final TokenProcessService tokenProcessService;

    public RabbitMqListener(TokenMapper tokenMapper, TokenProcessService tokenProcessService) {
        this.tokenMapper = tokenMapper;
        this.tokenProcessService = tokenProcessService;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue_request}")
    public void consumer(ProcessMessage message) throws InterruptedException {
        log.info("Received Response Message {}", htmlEscape(message.toString()));
        var tokenDto = tokenMapper.toDto(message);
        tokenProcessService.process(tokenDto);
    }
}
