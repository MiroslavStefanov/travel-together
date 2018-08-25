package org.softuni.traveltogether.web.controllers;

import org.softuni.traveltogether.config.WebConstants;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController extends BaseController implements ErrorController {

    @RequestMapping(WebConstants.ERROR_URL)
    public ModelAndView error(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        return super.page("error/error-"+status);
    }

    @GetMapping(WebConstants.ACCESS_DENIED_URL)
    public ModelAndView unauthorized() {
        return super.page("error/error-403");
    }

    @Override
    public String getErrorPath() {
        return WebConstants.ERROR_URL;
    }
}
