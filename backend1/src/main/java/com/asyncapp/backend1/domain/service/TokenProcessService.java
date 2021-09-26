package com.asyncapp.backend1.domain.service;

import com.asyncapp.backend1.domain.model.TokenDto;

public interface TokenProcessService {
    void process(TokenDto tokenDto);
}
