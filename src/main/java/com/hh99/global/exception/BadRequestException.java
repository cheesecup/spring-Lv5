package com.hh99.global.exception;

public class BadRequestException extends GlobalException {

    public BadRequestException(ErrorMsg errorMsg) {
        super(errorMsg.getMessage());
    }

    @Override
    public int statusCode() {
        return 400;
    }
}
