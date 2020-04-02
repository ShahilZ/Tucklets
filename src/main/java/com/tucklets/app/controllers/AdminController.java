package com.tucklets.app.controllers;

import com.tucklets.app.containers.ImportChildrenContainer;
import com.tucklets.app.containers.enums.ImportStatus;
import com.tucklets.app.entities.Child;
import com.tucklets.app.services.ChildService;
import com.tucklets.app.utils.ExcelUtils;
import com.tucklets.app.utils.UploadChildrenDataHeader;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    ChildService childService;

    @GetMapping("/dashboard")
    public String viewDashboard(Model model) {
        List<Child> children = childService.fetchAllChildren();
        model.addAttribute("children", children);
        return "admin/dashboard";
    }

    @GetMapping("/dashboard/upload")
    public String upload() {
        return "admin/upload-children-data";
    }

    @PostMapping(value = "/upload-children-data")
    public ModelAndView uploadChildrenData(@RequestParam("file") MultipartFile excelData) throws IOException {

        ImportChildrenContainer importChildrenContainer = extractChildrenFromExcel(excelData);
        List<Child> children = importChildrenContainer.getChildren();

        // Handle empty list.
        if (!children.isEmpty()) {
            childService.addMultipleChildren(children);
        }

        ModelAndView modelAndView = new ModelAndView("admin/upload-result");
        modelAndView.addObject("importChildrenContainer", importChildrenContainer);

        return modelAndView;
    }


    /**
     * Extracts the children information from a given Excel file.
     */
    private ImportChildrenContainer extractChildrenFromExcel(MultipartFile excelData) throws IOException {
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
        for(int rowIndex = 1; rowIndex < excelRowCount; rowIndex++) {
            Map<UploadChildrenDataHeader, String> importChild = new HashMap<>();
            UploadChildrenDataHeader[] uploadChildrenDataHeaders = UploadChildrenDataHeader.values();

            XSSFRow row = worksheet.getRow(rowIndex);
            // Skip if empty
            if (row != null) {
                for (Map.Entry<UploadChildrenDataHeader, Integer> header: indexesToProcessMap.entrySet()) {
                    importChild.put(header.getKey(), ExcelUtils.fetchCellValueAtIndex(row, header.getValue()));
                }
            }
            if (validateExtractedChild(importChild)) {
                // Create Child object from excelChild
                Child child = convertExcelChild(importChild);
                    Child existingChild = childService.fetchChildByNameAndBirthYear(child.getFirstName(), child.getLastName(), child.getBirthYear());
                if (existingChild == null) {
                    numChildrenAdded++;
                }
                else {
                    // Child already exists, update childId so object can be updated.
                    addExistingFieldsToChild(child, existingChild);
                    numChildrenUpdated++;
                }
                children.add(child);

            }
        }
        return new ImportChildrenContainer(children, numChildrenUpdated, numChildrenAdded, ImportStatus.SUCCESS);
    }

    /**
     * Sets any additional fields for the imported Child object that is taken from his or her existing counterpart.
     * This method will port over items like childId and creationDate.
     */
    private void addExistingFieldsToChild(Child child, Child existingChild) {
        child.setChildId(existingChild.getChildId());
        child.setCreationDate(existingChild.getCreationDate());
        child.setSponsored(existingChild.isSponsored());
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
        child.setDesiredOccupation(importChild.get(UploadChildrenDataHeader.ASPIRATIONS));
        return child;
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

}
