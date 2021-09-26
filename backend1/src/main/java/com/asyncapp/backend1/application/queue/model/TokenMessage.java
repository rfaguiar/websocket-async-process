package com.asyncapp.backend1.application.queue.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenMessage {
    private String solicitationId;
    private String token;
}
