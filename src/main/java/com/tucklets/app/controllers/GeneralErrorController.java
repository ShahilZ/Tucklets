package com.tucklets.app.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@RestController
public class GeneralErrorController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    public ModelAndView handlerError(HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        if (response.getStatus() == HttpStatus.NOT_FOUND.value()) {
            System.out.println(response);
//            modelAndView.setViewName(String.format("redirect:%s", Constants.TUCKLETS_URL));
        }
        else {
            modelAndView.setViewName("/error");
        }

        return modelAndView;
    }
}