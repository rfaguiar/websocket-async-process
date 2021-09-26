package com.asyncapp.backend1.application.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TokenRequest {

    @NotNull
    @NotBlank
    private String username;
}
