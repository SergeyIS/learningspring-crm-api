package com.learningspring.crm.api.exception;

public class BadRequestApiException extends ApiException {

    public BadRequestApiException() {
        this(null);
    }

    public BadRequestApiException(Throwable cause) {
        super("Bad request", 400, cause);
    }
}
