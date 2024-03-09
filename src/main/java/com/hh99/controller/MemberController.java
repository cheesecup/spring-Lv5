package com.hh99.controller;

import com.hh99.dto.request.MemberRequestDTO;
import com.hh99.dto.response.MemberResponseDTO;
import com.hh99.global.response.SuccessResponseDTO;
import com.hh99.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/member")
    public ResponseEntity<SuccessResponseDTO<MemberResponseDTO>> signup(@RequestBody @Valid MemberRequestDTO requestDTO) {
        MemberResponseDTO responseDTO = memberService.signup(requestDTO);

        return ResponseEntity.ok(new SuccessResponseDTO<MemberResponseDTO>("회원가입 성공", responseDTO));
    }
}
