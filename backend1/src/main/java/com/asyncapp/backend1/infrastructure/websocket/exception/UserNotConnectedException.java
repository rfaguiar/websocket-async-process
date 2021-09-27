package com.asyncapp.backend1.infrastructure.websocket.exception;

public class UserNotConnectedException extends RuntimeException{

    public UserNotConnectedException(String message) {
        super(message);
    }
}
