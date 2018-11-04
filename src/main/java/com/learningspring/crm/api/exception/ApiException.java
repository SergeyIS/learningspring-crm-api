package com.learningspring.crm.api.exception;

public class ApiException extends RuntimeException {

    private static final String DEFAULT_EXCEPTION_DESCRIPTION = "Something gone wrong";

    private Integer status;
    private String description;

    public ApiException(Integer status) {
        this(DEFAULT_EXCEPTION_DESCRIPTION, status);
    }

    public ApiException(String message, Integer status) {
        this(message, status, DEFAULT_EXCEPTION_DESCRIPTION);
    }

    public ApiException(String message, Integer status, String description) {
        this(message, status, description, null);
    }

    public ApiException(String message, int status, Throwable cause) {
        this(message, status, DEFAULT_EXCEPTION_DESCRIPTION, cause);
    }

    public ApiException(String message, Integer status, String description, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.description = description;
    }


    public Integer getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }
}
