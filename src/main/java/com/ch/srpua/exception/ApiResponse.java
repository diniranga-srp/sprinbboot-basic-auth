package com.ch.srpua.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    private Integer status;
    private String message;

    public static ApiResponse of(Integer status, String message) {
        return new ApiResponse(status, message);
    }
}

