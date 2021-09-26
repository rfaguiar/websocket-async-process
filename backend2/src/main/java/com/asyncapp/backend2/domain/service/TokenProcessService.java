package com.asyncapp.backend2.domain.service;

import com.asyncapp.backend2.domain.model.TokenDto;

public interface TokenProcessService {
    void process(TokenDto tokenDto) throws InterruptedException;
}
