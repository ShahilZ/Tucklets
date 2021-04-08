package com.tucklets.app.containers;

import com.tucklets.app.entities.enums.SponsorInfoStatus;


public class ResultContainer {

    private SponsorInfoStatus status;
    private String errors;

    public ResultContainer(){}

    public ResultContainer(SponsorInfoStatus status, String errors) {
        this.status = status;
        this.errors = errors;
    }

    public SponsorInfoStatus getStatus() {
        return status;
    }

    public void setStatus(SponsorInfoStatus status) { this.status = status; }

    public String getErrors() { return errors; }

    public void setErrors(String errors) { this.errors = errors; }
}
