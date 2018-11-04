package com.learningspring.crm.api.exception;

public class InternalServerErrorApiException extends ApiException {

    public InternalServerErrorApiException() {
        this((Throwable) null);
    }

    public InternalServerErrorApiException(Throwable cause) {
        super("Internal server error", 500, cause);
    }

    public InternalServerErrorApiException(String message) {
        super(message, 500);
    }
}
