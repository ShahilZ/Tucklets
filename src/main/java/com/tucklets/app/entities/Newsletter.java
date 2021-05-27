package com.tucklets.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "Newsletter")
public class Newsletter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "newsletter_id", updatable = false, nullable = false)
    private Long newsletterId;

    @Column(name = "filename", nullable = false)
    private String filename;

    @Column(name = "upload_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadDate;

    @Column(name = "newsletter_location")
    private String newsletterLocation;

    public Newsletter() {};

    public Newsletter(String filename, Date uploadDate, String newsletterLocation) {
        this.filename = filename;
        this.uploadDate = uploadDate;
        this.newsletterLocation = newsletterLocation;
    }

    public Long getNewsletterId() {
        return newsletterId;
    }

    public String getFilename() {
        return filename;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public String getNewsletterLocation() {
        return newsletterLocation;
    }

}
