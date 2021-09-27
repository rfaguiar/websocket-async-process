package com.asyncapp.backend1.domain.service;

import com.asyncapp.backend1.domain.model.JwtDto;

public interface TokenCallbackService {
    void callbackToUser(JwtDto jwtDto);
}
