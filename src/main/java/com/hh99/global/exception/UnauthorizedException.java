package com.hh99.global.exception;

public class UnauthorizedException extends GlobalException{

    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }

    @Override
    public int statusCode() {
        return 401;
    }
}
