package com.asyncapp.backend1.infrastructure.service;

import com.asyncapp.backend1.infrastructure.model.ProcessMessage;
import com.asyncapp.backend1.domain.model.TokenDto;
import com.asyncapp.backend1.domain.mapper.TokenMapper;
import com.asyncapp.backend1.domain.service.TokenProcessService;
import com.asyncapp.backend1.infrastructure.queue.RabbitmqProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static org.springframework.web.util.HtmlUtils.htmlEscape;

@Slf4j
@Service
public class TokenProcessServiceImpl implements TokenProcessService {

    private final AmqpTemplate amqpTemplate;
    private final RabbitmqProperties configRabbitmqProperties;
    private final TokenMapper tokenMapper;

    public TokenProcessServiceImpl(AmqpTemplate amqpTemplate,
                                   RabbitmqProperties configRabbitmqProperties,
                                   TokenMapper tokenMapper) {
        this.amqpTemplate = amqpTemplate;
        this.configRabbitmqProperties = configRabbitmqProperties;
        this.tokenMapper = tokenMapper;
    }

    @Override
    public void process(TokenDto tokenDto) {
      var exchange = configRabbitmqProperties.getExchangeRequest();
      var routingKey = configRabbitmqProperties.getRoutingkey();
      var message = tokenMapper.toMessage(tokenDto);
      amqpTemplate.convertAndSend(exchange, routingKey, message, m -> {
          m.getMessageProperties().getHeaders().put("solicitationId", tokenDto.getSolicitationId());
          return m;
      });
      log.info("Sent Solicitation {} to process queue", htmlEscape(tokenDto.toString()));
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue_response}")
    public void consumer(ProcessMessage message) {
        log.info("Received Response Message {}", htmlEscape(message.toString()));
    }

}
