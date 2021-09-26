package com.asyncapp.backend1.application.rest;

import com.asyncapp.backend1.domain.service.TokenProcessService;
import com.asyncapp.backend1.application.request.TokenRequest;
import com.asyncapp.backend1.application.response.TokenResponse;
import com.asyncapp.backend1.domain.mapper.TokenMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/token")
public class TokenController {

    private final TokenProcessService tokenProcessService;
    private final TokenMapper tokenMapper;

    public TokenController(TokenProcessService tokenProcessService, TokenMapper tokenMapper) {
        this.tokenProcessService = tokenProcessService;
        this.tokenMapper = tokenMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TokenResponse createToken(@Valid @RequestBody TokenRequest tokenRequest) {
        var tokenDto = tokenMapper.toDto(tokenRequest);
        tokenProcessService.process(tokenDto);
        return tokenMapper.toResponse(tokenDto);
    }

}
