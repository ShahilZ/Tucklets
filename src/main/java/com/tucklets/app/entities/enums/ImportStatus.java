package com.tucklets.app.entities.enums;

public enum ImportStatus {

    SUCCESS(false, "Success"),
    MISMATCHING_HEADERS(true, " Required headers/column names are missing for the uploaded file.");

    private boolean hasErrors;

    private String statusMessage;

    ImportStatus(boolean hasErrors, String statusMessage) {
        this.hasErrors = hasErrors;
        this.statusMessage = statusMessage;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public boolean getHasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }
}
