package com.tucklets.app.services;

import com.tucklets.app.containers.admin.ImportChildrenContainer;
import com.tucklets.app.entities.Child;
import com.tucklets.app.entities.enums.ImportStatus;
import com.tucklets.app.utils.ExcelUtils;
import com.tucklets.app.utils.UploadChildrenDataHeader;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public  class ExcelService {

    @Autowired
    ChildService childService;

    @Autowired
    ManageChildrenService manageChildrenService;

    /**
     * Extracts the children information from a given Excel file.
     */
    public ImportChildrenContainer extractChildrenFromExcel(MultipartFile excelData) throws IOException {
        List<Child> children = new ArrayList<>();
        int numChildrenUpdated = 0;
        int numChildrenAdded = 0;
        XSSFWorkbook workbook = new XSSFWorkbook(excelData.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);
        int excelRowCount = worksheet.getPhysicalNumberOfRows();

        // Process header row.
        Map<UploadChildrenDataHeader, Integer> indexesToProcessMap = ExcelUtils.fetchMatchingHeaderIndexValues(
                worksheet.getRow(0), UploadChildrenDataHeader.HEADER_TO_UPLOAD_CHILDREN_DATA_HEADER_MAP);

        // Validate that we have found matching headers for all required headers.
        if (indexesToProcessMap.size() != UploadChildrenDataHeader.HEADER_TO_UPLOAD_CHILDREN_DATA_HEADER_MAP.size()) {
            return new ImportChildrenContainer(new ArrayList<>(), 0, 0, ImportStatus.MISMATCHING_HEADERS);
        }

        // Skip initial header row.
        for (int rowIndex = 1; rowIndex < excelRowCount; rowIndex++) {
            Map<UploadChildrenDataHeader, String> importChild = new HashMap<>();

            XSSFRow row = worksheet.getRow(rowIndex);
            // Skip if empty
            if (row != null) {
                for (Map.Entry<UploadChildrenDataHeader, Integer> header : indexesToProcessMap.entrySet()) {
                    importChild.put(header.getKey(), ExcelUtils.fetchCellValueAtIndex(row, header.getValue()));
                }
            }
            if (validateExtractedChild(importChild)) {
                // Create Child object from excelChild
                Child child = convertExcelChild(importChild);
                Child existingChild = childService.fetchChildByNameAndBirthYear(child.getFirstName(), child.getLastName(), child.getBirthYear());
                if (existingChild == null) {
                    numChildrenAdded++;
                } else {
                    // Child already exists, update childId so object can be updated.
                    manageChildrenService.addExistingFieldsToChild(child, existingChild);
                    numChildrenUpdated++;
                }
                children.add(child);

            }
        }
        return new ImportChildrenContainer(children, numChildrenUpdated, numChildrenAdded, ImportStatus.SUCCESS);
    }

    /**
     * Validates the extracted Child object is valid.
     */
    private boolean validateExtractedChild(Map<UploadChildrenDataHeader, String> importChild) {
        for (Map.Entry<UploadChildrenDataHeader, String> header : importChild.entrySet()) {
            if (StringUtils.isBlank(header.getValue())) {
                return false;
            }
        }
        // TODO: More in-depth validation.
        return true;
    }

    /**
     * Converts the Map of Excel headers to excel values to a Child object.
     */
    private Child convertExcelChild(Map<UploadChildrenDataHeader, String> importChild) {
        Child child = new Child();
        child.setFirstName(importChild.get(UploadChildrenDataHeader.FIRST_NAME));
        child.setLastName(importChild.get(UploadChildrenDataHeader.LAST_NAME));
        child.setBirthYear(Integer.parseInt(importChild.get(UploadChildrenDataHeader.BIRTH_YEAR)));
        child.setGrade(Integer.parseInt(importChild.get(UploadChildrenDataHeader.GRADE)));
        child.setInformation(importChild.get(UploadChildrenDataHeader.INFORMATION));
        return child;
    }
}
