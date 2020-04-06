package com.tucklets.app.utils;

import com.tucklets.app.containers.LocaleContainer;
import org.springframework.context.i18n.LocaleContextHolder;

public class ContainerUtils {
    private ContainerUtils(){}

    public static LocaleContainer createLocaleContainer() {
        return new LocaleContainer(Constants.SUPPORTED_LOCALES, LocaleContextHolder.getLocale());
    }
}