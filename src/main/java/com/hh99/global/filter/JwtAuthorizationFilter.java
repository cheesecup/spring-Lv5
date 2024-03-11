package com.hh99.global.filter;

import com.hh99.global.jwt.JwtUtil;
import com.hh99.member.service.MemberService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// JWT 유효성 검증 및 인가 처리
@Slf4j(topic = "JWT 유효성 검증 및 인가 처리")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final MemberService memberService;

    // ServletRequest -> doFilter, OncePerRequestFilter -> doFilterInternal
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String tokenValue = jwtUtil.getJwtFromRequest(request);

        if (StringUtils.hasText(tokenValue)) {
            String token = jwtUtil.substringToken(tokenValue);

            if (!jwtUtil.validateToken(token)) {
                return;
            }

            Claims claims = jwtUtil.getMemberInfoFromToken(token);

            try {
                // `Token`을 사용한 인증을 진행하기 때문에 직접 인증객체를 만들고 인증 처리
                setAuthentication(claims.get("email", String.class));
            } catch (Exception e) {
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    // 토큰을 이용한 인증 처리
    // SecurityContextHolder > SecurityContext > Authentication > Principal / Credentials / Authorities
    public void setAuthentication(String email) {
        SecurityContext context = SecurityContextHolder.createEmptyContext(); // 비어있는 `SecurityContext` 생성
        Authentication authentication = createAuthentication(email); // 인증 객체 생성 `Principal`, `Credentials`, `Authorities`를 넣어준다.
        context.setAuthentication(authentication); // 생성된 인증 객체를 `context`에 넣어주기

        SecurityContextHolder.setContext(context);
    }

    // `context`에 넣어줄 인증 객체 생성
    // Principal : 사용자 식별, `UserDetails` 인스턴스를 넣는다
    // Credential : 비밀번호, 사용자 인증에 사용 후 비운다.
    // Authorities : 사용자 권한을 `GrantedAuthority`로 추상화하여 사용
    private Authentication createAuthentication(String email) throws UsernameNotFoundException {
        UserDetails userDetails = memberService.loadUserByUsername(email);

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
