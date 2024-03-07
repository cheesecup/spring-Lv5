package com.hh99.service;

import com.hh99.dto.request.MemberRequestDTO;
import com.hh99.dto.response.MemberResponseDTO;
import com.hh99.entity.Member;
import com.hh99.global.exception.ErrorCode;
import com.hh99.global.exception.NotFoundException;
import com.hh99.global.security.UserDetailsImpl;
import com.hh99.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j(topic = "MemberService")
@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public MemberResponseDTO signup(MemberRequestDTO requestDTO) {
        Member member = memberRepository.save(new Member(requestDTO, passwordEncoder));

        return modelMapper.map(member, MemberResponseDTO.class);
    }

    // 회원 정보를 시큐리티의 인증 관리자에게 전달
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXIST_EMAIL));

        return new UserDetailsImpl(member);
    }
}
