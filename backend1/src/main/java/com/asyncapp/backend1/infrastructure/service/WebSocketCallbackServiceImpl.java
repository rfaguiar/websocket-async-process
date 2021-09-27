package com.asyncapp.backend1.infrastructure.service;

import com.asyncapp.backend1.domain.model.JwtDto;
import com.asyncapp.backend1.domain.service.TokenCallbackService;
import com.asyncapp.backend1.infrastructure.websocket.exception.UserNotConnectedException;
import com.asyncapp.backend1.infrastructure.websocket.model.WebSocketMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;

import static java.text.MessageFormat.format;

@Slf4j
@Service
public class WebSocketCallbackServiceImpl implements TokenCallbackService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final SimpUserRegistry simpUserRegistry;

    public WebSocketCallbackServiceImpl(SimpMessagingTemplate simpMessagingTemplate,
                                        SimpUserRegistry simpUserRegistry) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.simpUserRegistry = simpUserRegistry;
    }

    @Override
    public void callbackToUser(JwtDto jwtDto) {
        try {
            simpUserRegistry.getUsers()
                    .stream()
                    .filter(simpUser -> simpUser.getName().equals(jwtDto.getUsername()))
                    .findFirst()
                    .orElseThrow(() -> new UserNotConnectedException(
                            format("Username {0}, solicitationId {1}", jwtDto.getUsername(), jwtDto.getSolicitationId())
                    ));
            var destination =  format("/topic/{0}", jwtDto.getSolicitationId());
            simpMessagingTemplate.convertAndSendToUser(jwtDto.getUsername(),
                   destination,
                    new WebSocketMessage(jwtDto.getToken()));
            log.info("sent to websocket destination {}", destination);
        } catch (UserNotConnectedException ex) {
            log.error(ex.getMessage());
        }
    }
}
