package com.tucklets.app.containers;

import java.util.List;
import java.util.Locale;

public class LocaleContainer {

    /**
     * List of supported locales for the Tucklets Application.
     */
    private List<Locale> supportedLocales;

    /**
     * User-selected locale.
     */
    private Locale selectedLocale;

    public LocaleContainer(List<Locale> supportedLocales, Locale selectedLocale) {
        this.supportedLocales = supportedLocales;
        this.selectedLocale = selectedLocale;
    }

    public List<Locale> getSupportedLocales() {
        return supportedLocales;
    }

    public Locale getSelectedLocale() {
        return selectedLocale;
    }
}
