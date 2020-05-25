package com.tucklets.app.containers;

public class LocaleDescriptionContainer {

    private String localeString;
    private String localeDisplayName;

    public LocaleDescriptionContainer(String localeString, String localeDisplayName) {
        this.localeString = localeString;
        this.localeDisplayName = localeDisplayName;
    }

    public String getLocaleString() {
        return localeString;
    }

    public String getLocaleDisplayName() {
        return localeDisplayName;
    }
}
