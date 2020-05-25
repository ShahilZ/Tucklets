package com.tucklets.app.utils;

import com.tucklets.app.containers.LocaleContainer;
import com.tucklets.app.containers.LocaleDescriptionContainer;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.List;
import java.util.stream.Collectors;

public class ContainerUtils {
    private ContainerUtils(){}

    public static LocaleContainer createLocaleContainer() {
        List<LocaleDescriptionContainer> localeDescriptionContainers = Constants.SUPPORTED_LOCALES
            .stream()
            .map(locale -> new LocaleDescriptionContainer(locale.toLanguageTag(), locale.getDisplayName()))
            .collect(Collectors.toList());
        return new LocaleContainer(localeDescriptionContainers, LocaleContextHolder.getLocale().toLanguageTag());
    }
}