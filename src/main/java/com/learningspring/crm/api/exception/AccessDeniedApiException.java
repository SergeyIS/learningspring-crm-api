package com.learningspring.crm.api.exception;

public class AccessDeniedApiException extends ApiException{

    public AccessDeniedApiException() {
        this(null);
    }

    public AccessDeniedApiException(Throwable cause) {
        super("Access denied", 403, cause);
    }
}
