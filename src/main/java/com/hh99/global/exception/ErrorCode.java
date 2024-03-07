package com.hh99.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    NOT_FOUND_MEMBER("해당 회원이 존재하지 않습니다."),
    NOT_EXIST_PASSWORD("비밀번호가 틀렸습니다."),
    NOT_EXIST_EMAIL("이메일이 존재하지 않습니다.");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }
}
