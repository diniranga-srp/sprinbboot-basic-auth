package com.ch.srpua.exception;

import lombok.Getter;

@Getter
public class UserError extends RuntimeException {
    private int code;

    public UserError(String message) {
        super(message);
    }

    public UserError(int code, String message) {
        super(message);
        this.code = code;
    }

}

