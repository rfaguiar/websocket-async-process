package com.asyncapp.backend2.infrastructure.service;

import com.asyncapp.backend2.domain.mapper.TokenMapper;
import com.asyncapp.backend2.domain.model.JwtDto;
import com.asyncapp.backend2.domain.model.TokenDto;
import com.asyncapp.backend2.domain.service.TokenProcessService;
import com.asyncapp.backend2.infrastructure.queue.RabbitmqProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
    public void process(TokenDto tokenDto) throws InterruptedException {
        TimeUnit.SECONDS.sleep(new Random().nextInt(7) + 3);// simulate integrations process time
        var jwtDto = generateJwtToken(tokenDto);
        var exchange = configRabbitmqProperties.getExchangeResponse();
        var routingKey = configRabbitmqProperties.getRoutingkey();
        var message = tokenMapper.toMessage(jwtDto);
        amqpTemplate.convertAndSend(exchange, routingKey, message, m -> {
            m.getMessageProperties().getHeaders().put("solicitationId", tokenDto.getSolicitationId());
            return m;
        });
        log.info("Sent token {} to process queue", htmlEscape(jwtDto.toString()));
    }

    private JwtDto generateJwtToken(TokenDto tokenDto) {
        var secret = "mysecret";
        var claims = new HashMap<String, Object>();
        claims.put("username", tokenDto.getUsername());
        var token = Jwts.builder()
                .setClaims(claims)
                .setSubject(tokenDto.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 5 * 60 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        return new JwtDto(tokenDto.getSolicitationId(), tokenDto.getUsername(), token);
    }
}
