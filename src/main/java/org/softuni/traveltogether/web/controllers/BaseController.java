package org.softuni.traveltogether.web.controllers;

import org.springframework.web.servlet.ModelAndView;

public abstract class BaseController {
    private static final String BASE_PAGE_LAYOUT_VIEW_NAME = "base-layout";
    private static final String BASE_PAGE_VIEW_NAME = "view";
    private static final String DEFAULT_MODEL_NAME = "model";

    protected ModelAndView view(String viewName) {
        return this.view(viewName, null);
    }

    protected ModelAndView view(String viewName, Object model) {
        return this.view(viewName, model, DEFAULT_MODEL_NAME);
    }

    protected ModelAndView view(String viewName, Object model, String modelName) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(modelName, model);
        modelAndView.addObject(BASE_PAGE_VIEW_NAME, viewName);
        modelAndView.setViewName(BASE_PAGE_LAYOUT_VIEW_NAME);

        return modelAndView;
    }

    protected ModelAndView redirect(String location) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:" + location);

        return modelAndView;
    }
}
