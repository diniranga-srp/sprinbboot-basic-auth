package com.example.demo.exception;

/**
 * Simple exception class for user errors.
 */
public class UserError extends RuntimeException {
    private int code;

    public UserError(String message) {
        super(message);
    }

    public UserError(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

