package com.learningspring.crm.api.model;

import com.learningspring.crm.api.exception.ApiException;

public class ExceptionModel {

    private Integer status;
    private String description;
    private Throwable cause;

    public ExceptionModel(ApiException e) {
        this(e.getStatus(), e.getDescription(), e.getCause());
    }

    public ExceptionModel(Integer status, String description, Throwable exception) {
        this.status = status;
        this.description = description;
        this.cause = exception;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Throwable getCause() {
        return cause;
    }

    public void setCause(Exception cause) {
        this.cause = cause;
    }
}
