package com.tucklets.app.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Constants {
    private Constants(){}

    public static final String TUCKLETS_URL = "https://www.tucklets.org";
    public static final double CHILD_DONATION_AMOUNT = 30;
    public static final List<Locale> SUPPORTED_LOCALES = Arrays.asList(Locale.US, Locale.TRADITIONAL_CHINESE);
    public static final String CHILD_QR_CODE_BASE = "https://tucklets.herokuapp.com/sponsor-info/?childId=";
    public static final String CHILDREN_EXPORT_FILENAME = "children-export.pdf";
    public static final String DEFAULT_IMAGE_LOCATION = "test-kid.png";
}
