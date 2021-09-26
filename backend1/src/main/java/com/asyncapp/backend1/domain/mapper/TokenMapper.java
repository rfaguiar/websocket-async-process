package com.asyncapp.backend1.domain.mapper;

import com.asyncapp.backend1.application.queue.model.TokenMessage;
import com.asyncapp.backend1.application.request.TokenRequest;
import com.asyncapp.backend1.application.response.TokenResponse;
import com.asyncapp.backend1.domain.model.JwtDto;
import com.asyncapp.backend1.domain.model.TokenDto;
import com.asyncapp.backend1.infrastructure.model.ProcessMessage;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface TokenMapper {
    TokenDto toDto(TokenRequest tokenRequest);
    ProcessMessage toMessage(TokenDto tokenDto);
    TokenResponse toResponse(TokenDto tokenDto);

    JwtDto toTokenDto(TokenMessage message);
}
