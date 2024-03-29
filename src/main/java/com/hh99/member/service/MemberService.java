package com.hh99.member.service;

import com.hh99.global.exception.BadRequestException;
import com.hh99.global.exception.ErrorMsg;
import com.hh99.global.security.UserDetailsImpl;
import com.hh99.member.entity.Member;
import com.hh99.member.repository.MemberRepository;
import com.hh99.member.request.MemberRequestDTO;
import com.hh99.member.response.MemberResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j(topic = "MemberService")
@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Transactional
    public MemberResponseDTO signup(MemberRequestDTO requestDTO) {
        Member member = memberRepository.save(new Member(requestDTO, passwordEncoder));

        return modelMapper.map(member, MemberResponseDTO.class);
    }

    // 회원 정보를 시큐리티의 인증 관리자에게 전달
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException(ErrorMsg.NOT_FOUND_MEMBER));

        return new UserDetailsImpl(member);
    }
}
