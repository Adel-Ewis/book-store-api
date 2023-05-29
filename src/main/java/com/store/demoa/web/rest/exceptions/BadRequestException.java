package com.store.demoa.web.rest.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RuntimeException {
    private final String errorKey;
    private final String errorMessage;
    private final int statusCode = HttpStatus.BAD_REQUEST.value();


    public BadRequestException(String errorKey, String errorMessage) {
        super(errorMessage);
        this.errorKey = errorKey;
        this.errorMessage = errorMessage;
    }

    public String getErrorKey() {
        return errorKey;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
