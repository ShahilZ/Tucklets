package com.tucklets.app.utils;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

import java.util.HashMap;
import java.util.Map;

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

    /**
     * Compares the provided headers in the Excel Row to the target headers. Returns a list of the header indexes that match.
     * Returns a map containing the header string to its corresponding index of its row.
     *
     * This method exists to assist with parsing Excel files that contain more columns than the required ones. This method
     * iterates over the "target" headers and locates their corresponding indexes. Thus, we don't need to parse every
     * cell of the Excel file.
     *
     * The return object is a map of the UploadChildrenDataHeader enum to its corresponding index in the provided Row.
     * This object facilitates the conversion of the imported rows to Child objects.
     */
    public static Map<UploadChildrenDataHeader, Integer> fetchMatchingHeaderIndexValues(Row headerRow, Map<String, UploadChildrenDataHeader> targetHeaderMap) {
        Map<String, Integer> headerToIndexMap = new HashMap<>();
        Map<UploadChildrenDataHeader, Integer> uploadChildrenDataHeaderToIndexMap = new HashedMap<>();
        if (headerRow != null) {
            // Determine number of column in the header row.
            int numCells = headerRow.getPhysicalNumberOfCells();
            for (int headerIndex = 0; headerIndex < numCells; headerIndex++) {
                String header = fetchCellValueAtIndex(headerRow, headerIndex);
                // If we have not already processed the header (pick first header if duplicate) and the header is one of the target headers
                if (!headerToIndexMap.containsKey(header) && targetHeaderMap.containsKey(header)) {
                    // Record index.
                    headerToIndexMap.put(header, headerIndex);
                    // Add to the map we are returning.
                    uploadChildrenDataHeaderToIndexMap.put(targetHeaderMap.get(header), headerIndex);
                }
            }
            return uploadChildrenDataHeaderToIndexMap;
        }
        return uploadChildrenDataHeaderToIndexMap;
    }
}
