package com.tucklets.app.utils;

import java.time.LocalDate;

public class CalculationUtils {
    private CalculationUtils(){}

    /**
     * Determines the age given a (birth) year.
     */
    public static int calculateAge(int year) {
        return LocalDate.now().getYear() - year;
    }
}
