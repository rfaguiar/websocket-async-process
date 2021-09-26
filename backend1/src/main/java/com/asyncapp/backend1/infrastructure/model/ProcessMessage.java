package com.asyncapp.backend1.infrastructure.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessMessage {
    private String solicitationId;
    private String username;
}
