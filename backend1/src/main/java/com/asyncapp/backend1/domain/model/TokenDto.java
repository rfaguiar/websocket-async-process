package com.asyncapp.backend1.domain.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class TokenDto {
    private String solicitationId;
    private String username;

    public TokenDto() {
        solicitationId = generateSolicitationId();
    }

    public TokenDto(String username) {
        solicitationId = generateSolicitationId();
        this.username = username;
    }

    private String generateSolicitationId() {
        return UUID.randomUUID().toString();
    }
}
