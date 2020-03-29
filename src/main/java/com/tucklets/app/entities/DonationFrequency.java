package com.tucklets.app.entities;

public enum DonationFrequency
{
    ONE_TIME("One Time", 1), MONTHLY("Monthly", 2), YEARLY("Yearly", 3);

    private String displayText;
    private int dbInt;
    
    DonationFrequency(String displayText, int dbInt) {
        this.displayText = displayText;
        this.dbInt = dbInt;
    }
}
