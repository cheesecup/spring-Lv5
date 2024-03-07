package com.hh99.global.exception;

public class NotFoundException extends GlobalException{

    public NotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }

    @Override
    public int statusCode() {
        return 404;
    }
}
