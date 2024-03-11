package com.hh99.global.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuccessResponseDTO<T> {
    private int statusCode = 200;
    private String message;
    private T data;

    public SuccessResponseDTO() {}

    public SuccessResponseDTO(String message) {
        this.message = message;
    }

    public SuccessResponseDTO(String message, T data) {
        this.message = message;
        this.data = data;
    }
}
