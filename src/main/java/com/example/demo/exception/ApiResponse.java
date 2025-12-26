package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Standard API response DTO.
 */
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

