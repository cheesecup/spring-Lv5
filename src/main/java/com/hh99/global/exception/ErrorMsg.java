package com.hh99.global.exception;

import lombok.Getter;

@Getter
public enum ErrorMsg {

    NOT_FOUND_ITEM("상품을 찾을 수 없습니다."),
    NOT_FOUND_MEMBER("회원을 찾을 수 없습니다."),
    NOT_HAVE_PERMISSION("권한이 없습니다.");

    private final String message;

    ErrorMsg(String message) {
        this.message = message;
    }
}
