package com.hh99.member.entity;

import com.hh99.global.entity.BaseTime;
import com.hh99.member.request.MemberRequestDTO;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Entity
public class Member extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String phone;

    private String address;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    protected Member() {}

    public Member(MemberRequestDTO requestDTO, PasswordEncoder passwordEncoder) {
        String encodePassword = passwordEncoder.encode(requestDTO.getPassword());
        this.email = requestDTO.getEmail();
        this.password = encodePassword;
        this.gender = requestDTO.getGender();
        this.phone = requestDTO.getPhone();
        this.address = requestDTO.getAddress();
        this.role = requestDTO.getRole();
    }
}
