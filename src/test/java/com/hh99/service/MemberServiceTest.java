package com.hh99.service;

import com.hh99.dto.request.MemberRequestDTO;
import com.hh99.dto.response.MemberResponseDTO;
import com.hh99.entity.Gender;
import com.hh99.entity.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    MemberRequestDTO createMemberDTO() {
        return MemberRequestDTO.builder()
                .email("test@email.com")
                .password("TestPwd99@")
                .gender(Gender.MALE)
                .phone("01012341234")
                .address("서울특별시 강남구")
                .role(Role.ADMIN)
                .build();
    }

    @Test
    @DisplayName("회원가입 테스트")
    void SignUp_Test() {
        // given
        MemberRequestDTO requestDTO = createMemberDTO();

        // when
        MemberResponseDTO responseDTO = memberService.signup(requestDTO);

        // then
        assertEquals(requestDTO.getEmail(), responseDTO.getEmail());
    }
}