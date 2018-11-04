package com.learningspring.crm.api.exception;

public class NotFoundApiException extends ApiException {

    public NotFoundApiException() {
        this(null);
    }

    public NotFoundApiException(Throwable cause) {
        super("Not found", 404, cause);
    }
}
