package com.tucklets.app.controllers.admin;

import com.google.zxing.WriterException;
import com.tucklets.app.containers.admin.ChildDetailsContainer;
import com.tucklets.app.containers.admin.ImportChildrenContainer;
import com.tucklets.app.entities.Child;
import com.tucklets.app.services.*;
import com.tucklets.app.utils.ContainerUtils;
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
import java.util.Base64;
import java.util.List;


@Controller
@RequestMapping(value = "/admin/children-dashboard")
public class ChildrenDashboardController {

    @Autowired
    ChildService childService;

    @Autowired
    PdfService pdfService;

    @Autowired
    ManageChildrenService manageChildrenService;

    @Autowired
    ExcelService excelService;

    @GetMapping("/")
    public String viewDashboard(Model model)     {
        List<Child> children = childService.fetchAllActiveChildren();
        List<ChildDetailsContainer> childrenDetails = manageChildrenService.createChildDetailsContainers(children);
        model.addAttribute("childrenDetails", childrenDetails);
        model.addAttribute("child", new Child());
        model.addAttribute("localeContainer", ContainerUtils.createLocaleContainer());
        return "admin/children-dashboard";
    }

    @DeleteMapping("/remove-child")
    public String removeChild(@RequestParam("childId") Long childId) {
        childService.deleteChild(childId);
        return "success";
    }

    @GetMapping("/upload")
    public String upload(Model model) {
        model.addAttribute("localeContainer", ContainerUtils.createLocaleContainer());
        return "admin/upload-children-data";
    }

    /**
     * This endpoint is to retrieve the child object to be passed to the Child Modal.
     */
    @GetMapping(value = "/edit-child")
    public String retrieveEditChildInfo(@RequestParam(value = "childId") String id,  Model model) {
        Long childId = Long.valueOf(id);
        Child child = childService.fetchChildById(childId);
        model.addAttribute("childDetails", manageChildrenService.createChildDetails(child));
        return "admin/child-modal :: modify-child-modal";
    }

    /**
     * This endpoint is to retrieve an empty child object to be passed to the Child Modal.
     */
    @GetMapping(value = "/add-child")
    public String retrieveAddChildInfo(Model model) {
        ChildDetailsContainer childDetails = new ChildDetailsContainer();
        childDetails.setChild(new Child());
        model.addAttribute("childDetails", childDetails);
        return "admin/child-modal :: modify-child-modal";
    }

    @PostMapping(value = "/submit-child")
    public String modifyChild(@ModelAttribute ChildDetailsContainer childDetails) throws IOException {
        manageChildrenService.updateChildAndDetails(childDetails);
        return "redirect:/admin/children-dashboard/";
    }

    @GetMapping(value = "/export-pdf", produces = MediaType.APPLICATION_PDF_VALUE)
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

        ImportChildrenContainer importChildrenContainer = excelService.extractChildrenFromExcel(excelData);
        List<Child> children = importChildrenContainer.getChildren();

        // Handle empty list.
        if (!children.isEmpty()) {
            childService.addMultipleChildren(children);
        }

        ModelAndView modelAndView = new ModelAndView("admin/upload-result");
        modelAndView.addObject("importChildrenContainer", importChildrenContainer);
        modelAndView.addObject("localeContainer", ContainerUtils.createLocaleContainer());

        return modelAndView;
    }
}
