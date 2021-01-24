package com.tucklets.app.utils;

import org.apache.commons.text.StringEscapeUtils;
import org.thymeleaf.util.StringUtils;

public class TextUtils {

    private TextUtils() {};

    /**
     * Cleans up the given string. Used mainly for storage to db.
     */
    public static String cleanString(String text) {
        String strippedText = lowerAndStrip(text);
        // If string is empty, return it.
        if (StringUtils.isEmpty(strippedText)) {
            return null;
        }
        // Escapes the string and removes all escaped html characters.
        String escapedText = StringEscapeUtils.escapeHtml4(strippedText);
        // Remove any suspicious characters.
        return escapedText.replaceAll("[\\\\<>/&;]", "");
    }

    /**
     * Lowers and strips the string of whitespace.
     */
    public static String lowerAndStrip(String text) {
        if (StringUtils.isEmpty(text)) {
            return null;
        }
        // Removes whitespace and lowercases the string.
        return text.strip().toLowerCase();
    }


}
