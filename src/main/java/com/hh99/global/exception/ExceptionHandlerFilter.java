package com.hh99.global.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh99.global.response.ErrorResponseDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
           filterChain.doFilter(request, response);
        } catch (NotFoundException e) {
            ObjectMapper objectMapper = new ObjectMapper();
            response.setStatus(401);
            response.setContentType("application/json; charset=UTF-8");

            response.getWriter().write(objectMapper.writeValueAsString(new ErrorResponseDTO<>(401, e.getMessage(), null)));
        }
    }
}
