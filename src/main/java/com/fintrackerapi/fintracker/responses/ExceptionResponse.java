package com.fintrackerapi.fintracker.responses;

public class ExceptionResponse {
    private Integer status;
    private String title;
    private String description;

    public ExceptionResponse(Integer status, String title, String description) {
        this.status = status;
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}
