package com.tucklets.app.controllers;

import com.google.zxing.WriterException;
import com.tucklets.app.containers.ImportChildrenContainer;
import com.tucklets.app.containers.enums.ImportStatus;
import com.tucklets.app.entities.Child;
import com.tucklets.app.services.ChildService;
import com.tucklets.app.utils.ContainerUtils;
import com.tucklets.app.services.PdfService;
import com.tucklets.app.utils.ExcelUtils;
import com.tucklets.app.utils.UploadChildrenDataHeader;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    ChildService childService;

    @Autowired
    PdfService pdfService;

    @GetMapping("/dashboard")
    public String viewDashboard(Model model) {
        List<Child> children = childService.fetchAllActiveChildren();
        model.addAttribute("children", children);
        model.addAttribute("child", new Child());
        model.addAttribute("localeContainer", ContainerUtils.createLocaleContainer());

        return "admin/dashboard";
    }

    @DeleteMapping("/remove-child")
    public String removeChild(@RequestParam("childId") Long childId) {
        childService.deleteChild(childId);
        return "success";
    }

    @GetMapping("/dashboard/upload")
    public String upload(Model model) {
        model.addAttribute("localeContainer", ContainerUtils.createLocaleContainer());
        return "admin/upload-children-data";
    }


    @GetMapping(value = "/retrieve-edit-child/")
    public String retrieveEditChildInfo(@RequestParam(value = "childId") String id,  Model model) {
        Long childId = Long.valueOf(id);
        Child child = childService.fetchChildById(childId);
        model.addAttribute("child", child);
        return "admin/modify-child-modal :: modify-child-modal";
    }

    @GetMapping(value = "/retrieve-add-child/")
    public String retrieveAddChildInfo(Model model) {
        // just takes in a new Child object for front end to have the child object
        model.addAttribute("child", new Child());
        return "admin/modify-child-modal :: modify-child-modal";
    }

    @PostMapping(value = "/dashboard/modify-child")
    public String modifyChild(@ModelAttribute Child child) {
        Child existingChild = childService.fetchChildById(child.getChildId());
        boolean isSponsored = child.getSponsored();
        addExistingFieldsToChild(child, existingChild);
        // Resetting to user provided value
        child.setSponsored(isSponsored);
        childService.addChild(child);
        return "redirect:/admin/dashboard";
    }

    @GetMapping(value = "/dashboard/export-pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> exportChildrenPdf() throws WriterException, IOException {
        ByteArrayOutputStream pdf = pdfService.generateActiveChildrenPdf(childService.fetchAllActiveChildren());
        byte[] encodedBytes = Base64.getEncoder().encode(pdf.toByteArray());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.setContentDispositionFormData("children-printouts.pdf", "children-printouts.pdf");
        headers.setContentLength(encodedBytes.length);

        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .contentLength(encodedBytes.length)
            .body(encodedBytes);
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
        for (int rowIndex = 1; rowIndex < excelRowCount; rowIndex++) {
            Map<UploadChildrenDataHeader, String> importChild = new HashMap<>();
            UploadChildrenDataHeader[] uploadChildrenDataHeaders = UploadChildrenDataHeader.values();

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
        child.setSponsored(existingChild.getSponsored());
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
