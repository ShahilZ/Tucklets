package com.tucklets.app.containers.admin;

public class NewsletterStatusContainer {

    public enum NewsletterStatus {
        SUCCESS("Success"), ERROR("Error");

        String status;

        NewsletterStatus(String status) {
            this.status = status;
        }
    }

    private NewsletterStatus newsletterStatus;
    private String newsletterName;
    private String msg;

    public NewsletterStatusContainer(NewsletterStatus newsletterStatus, String newsletterName, String msg) {
        this.newsletterStatus = newsletterStatus;
        this.newsletterName = newsletterName;
        this.msg = msg;
    }

    public NewsletterStatusContainer(NewsletterStatus newsletterStatus, String newsletterName) {
        this.newsletterStatus = newsletterStatus;
        this.newsletterName = newsletterName;
        this.msg = "";
    }

    public static NewsletterStatusContainer createErrorNewsletterStatusContainer(String errorMsg) {
        return new NewsletterStatusContainer(NewsletterStatus.ERROR, "", errorMsg);
    }

}
