package com.tucklets.app.controllers;

import com.tucklets.app.containers.ImportChildrenContainer;
import com.tucklets.app.entities.Child;
import com.tucklets.app.services.ChildService;
import com.tucklets.app.utils.ExcelUtils;
import com.tucklets.app.utils.UploadChildrenDataHeader;
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
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    ChildService childService;

    @GetMapping("/dashboard")
    public String viewDashboard(Model model) {
        List<Child> children = childService.fetchAllChildren();
        model.addAttribute("children", children);
        model.addAttribute("child", new Child());
        return "admin/dashboard";
    }

    @GetMapping("/dashboard/upload")
    public String upload() {
        return "admin/upload-children-data";
    }


    @GetMapping(value = "/retrieve-child/")
    public @ResponseBody Child retrieveChildInfo(@RequestParam(value = "childId") String id, Model model) {
        Long childId = Long.valueOf(id);
        Child child = childService.fetchChildById(childId);
        model.addAttribute("child", child);
        return child;
    }

    @PostMapping(value = "/dashboard/edit-child")
    public String editChild(@ModelAttribute Child child) {
        Child existingChild = childService.fetchChildById(child.getChildId());
        boolean isSponsored = child.isSponsored();
        addExistingFieldsToChild(child, existingChild);
        // Resetting to user provided value
        child.setSponsored(isSponsored);

        childService.addChild(child);
        return "redirect:/admin/dashboard";
    }

    @PostMapping(value = "/upload-children-data")
    public ModelAndView uploadChildrenData(@RequestParam("file") MultipartFile excelData) throws IOException {

        ImportChildrenContainer importChildrenContainer = extractChildrenFromExcel(excelData);
        List<Child> children = importChildrenContainer.getChildren();

        // Handle empty list.
        if (!children.isEmpty()) {
            childService.addMultipleChildren(children);
        }

        ModelAndView modelAndView = new ModelAndView("/admin/upload-success");
        modelAndView.addObject("numAddedChildren", importChildrenContainer.getNumChildrenAdded());
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

        // Skip initial header row.
        for(int rowIndex = 1; rowIndex < excelRowCount; rowIndex++) {
            Map<UploadChildrenDataHeader, String> excelChild = new HashMap<>();
            UploadChildrenDataHeader[] uploadChildrenDataHeaders = UploadChildrenDataHeader.values();

            XSSFRow row = worksheet.getRow(rowIndex);
            // Skip if empty
            if (row != null) {
                for (int headerIndex = 0; headerIndex < uploadChildrenDataHeaders.length; headerIndex++) {
                    excelChild.put(uploadChildrenDataHeaders[headerIndex], ExcelUtils.fetchCellValueAtIndex(row, headerIndex));
                }
            }
            // Create Child object from excelChild
            Child child = convertExcelChild(excelChild);
            if (validateExtractedChild(child)) {
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
        return new ImportChildrenContainer(children, numChildrenUpdated, numChildrenAdded);
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
    private Child convertExcelChild(Map<UploadChildrenDataHeader, String> excelChild) {
        Child child = new Child();
        child.setFirstName(excelChild.get(UploadChildrenDataHeader.FIRST_NAME));
        child.setLastName(excelChild.get(UploadChildrenDataHeader.LAST_NAME));
        child.setBirthYear(Integer.parseInt(excelChild.get(UploadChildrenDataHeader.BIRTH_YEAR)));
        child.setGrade(Integer.parseInt(excelChild.get(UploadChildrenDataHeader.GRADE)));
        child.setDesiredOccupation(excelChild.get(UploadChildrenDataHeader.ASPIRATIONS));
        return child;
    }

    /**
     * Validates the extracted Child object is valid.
     */
    private boolean validateExtractedChild(Child child) {
        // TODO: Validation.
        return true;
    }
}