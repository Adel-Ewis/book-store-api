package com.store.demoa.web.rest.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RuntimeException {
    private final String errorMessage;
    private final int statusCode = HttpStatus.NOT_FOUND.value();

    public NotFoundException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
