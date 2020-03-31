package com.tucklets.app.utils;

import java.time.LocalDate;

public class CalculationUtils {
    private CalculationUtils(){}

    public static int calculateAge(int year) {
        return LocalDate.now().getYear() - year;
    }
}
