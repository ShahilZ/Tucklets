package com.tucklets.app.utils;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

public class ExcelUtils {


    private ExcelUtils(){};

    /**
     * Determines whether the given row is empty.
     */
    public static boolean isRowEmpty(Row row) {
        DataFormatter dataFormatter = new DataFormatter();
        if (row == null) {
            return true;
        }
        for (Cell cell : row) {
            if (dataFormatter.formatCellValue(cell).trim().length() > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Given a row from an Excel file, extracts the value at the provided index.
     * This method will trim and clean the value as well.
     */
    public static String fetchCellValueAtIndex(Row row, int index) {
        DataFormatter dataFormatter = new DataFormatter();
        String cellValue = dataFormatter.formatCellValue(row.getCell(index)).trim();
        // Escape HTML, JS, etc.
        return StringEscapeUtils.escapeJava(cellValue);
    }
}
