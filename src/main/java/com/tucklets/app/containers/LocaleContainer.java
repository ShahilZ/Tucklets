package com.tucklets.app.containers;

import java.util.List;

public class LocaleContainer {

    /**
     * List of supported locales for the Tucklets Application.
     */
    private List<LocaleDescriptionContainer> supportedLocales;

    /**
     * User-selected locale.
     */
    private String selectedLocaleString;

    public LocaleContainer(List<LocaleDescriptionContainer> supportedLocales, String selectedLocaleString) {
        this.supportedLocales = supportedLocales;
        this.selectedLocaleString = selectedLocaleString;
    }

    public List<LocaleDescriptionContainer> getSupportedLocales() {
        return supportedLocales;
    }

    public String getSelectedLocaleString() {
        return selectedLocaleString;
    }
}
