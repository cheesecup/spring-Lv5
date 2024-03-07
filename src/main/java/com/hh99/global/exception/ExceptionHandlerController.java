package com.hh99.global.exception;

import com.hh99.global.response.ErrorResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO<Map<String, String>>> invalidFieldRequest(MethodArgumentNotValidException e) {
        ErrorResponseDTO<Map<String, String>> responseDTO = new ErrorResponseDTO<Map<String, String>>(400, "유효성 검사 실패", new HashMap<>());

        for (FieldError fieldError : e.getFieldErrors()) {
            responseDTO.getData().put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(400).body(responseDTO);
    }
}
