package com.asyncapp.backend2.domain.mapper;

import com.asyncapp.backend2.application.model.ProcessMessage;
import com.asyncapp.backend2.domain.model.JwtDto;
import com.asyncapp.backend2.domain.model.TokenDto;
import com.asyncapp.backend2.infrastructure.model.TokenMessage;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface TokenMapper {
    TokenDto toDto(ProcessMessage message);
    TokenMessage toMessage(JwtDto jwtDto);
}
