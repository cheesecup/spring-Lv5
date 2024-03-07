package com.hh99.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh99.dto.request.MemberRequestDTO;
import com.hh99.entity.Gender;
import com.hh99.entity.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

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
    @DisplayName("POST /api/member 테스트")
    void SignUp_POST_Request_Test() throws Exception {
        String json = objectMapper.writeValueAsString(createMemberDTO());

        mockMvc.perform(post("/api/member")
                .contentType("application/json")
                .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }

}