package com.hh99.global.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuccessResponseDTO<T> {
    private int statusCode;
    private String message;
    private T data;

    public SuccessResponseDTO() {}

    public SuccessResponseDTO(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public SuccessResponseDTO(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }
}
