package com.hh99.global.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh99.dto.request.LoginRequestDTO;
import com.hh99.entity.Member;
import com.hh99.global.jwt.JwtUtil;
import com.hh99.global.response.ErrorResponseDTO;
import com.hh99.global.response.SuccessResponseDTO;
import com.hh99.global.security.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

// 기존 `Security`가 사용하는 `Session` 방식이 아닌 JWT를 사용하기 위한 커스텀 클래스
@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/member/login");
    }

    // 로그인 수행
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 시도");
        try {
            LoginRequestDTO requestDTO = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDTO.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(requestDTO.getEmail(), requestDTO.getPassword(), null)
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    // 로그인 성공 `SecurityContextHolder`에 `Authentication(=UsernamePasswordAuthenticationToken)` 세팅
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authentication) throws IOException, ServletException {
        log.info("로그인 성공");
        Member member = ((UserDetailsImpl) authentication.getPrincipal()).getMember();

        String token = jwtUtil.createToken(member.getMemberId(), member.getEmail(), member.getRole());
        jwtUtil.addJwtToCookie(token, response);

        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(new SuccessResponseDTO<String>("로그인 성공", member.getEmail())));
    }

    // 로그인 실패
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("로그인 실패");

        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(401);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(new ErrorResponseDTO<>(401, "아이디나 비밀번호가 일치하지 않습니다.", null)));
    }
}
