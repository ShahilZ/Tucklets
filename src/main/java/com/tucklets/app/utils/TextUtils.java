package com.tucklets.app.utils;

import org.apache.commons.text.StringEscapeUtils;

public class TextUtils {

    private TextUtils() {};

    /**
     * Cleans up the given string. Used mainly for storage to db.
     */
    public static String cleanString(String text) {
        String strippedText = lowerAndStrip(text);
        // Escapes the string and removes all escaped characters.
        String escapedText = StringEscapeUtils.escapeJava(strippedText);
        // Remove any suspicious characters.
        return escapedText.replaceAll("[\\\\<>/&;]", "");
    }

    /**
     * Lowers and strips the string of whitespace.
     */
    public static String lowerAndStrip(String text) {
        // Removes whitespace and lowercases the string.
        return text.strip().toLowerCase();
    }


}
