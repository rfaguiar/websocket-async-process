package com.asyncapp.backend1.domain.mapper;

import com.asyncapp.backend1.infrastructure.model.ProcessMessage;
import com.asyncapp.backend1.application.request.TokenRequest;
import com.asyncapp.backend1.application.response.TokenResponse;
import com.asyncapp.backend1.domain.model.TokenDto;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface TokenMapper {
    TokenDto toDto(TokenRequest tokenRequest);
    ProcessMessage toMessage(TokenDto tokenDto);
    TokenResponse toResponse(TokenDto tokenDto);
}
