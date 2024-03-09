package com.hh99.member.response;

import com.hh99.member.entity.Gender;
import com.hh99.member.entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResponseDTO {
    private Long memberId;
    private String email;
    private Gender gender;
    private String phone;
    private String address;
    private Role role;

    public MemberResponseDTO() {}
}
