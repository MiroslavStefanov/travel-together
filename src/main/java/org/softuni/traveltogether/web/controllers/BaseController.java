package org.softuni.traveltogether.web.controllers;

import org.springframework.web.servlet.ModelAndView;

public abstract class BaseController {
    private static final String BASE_PAGE_LAYOUT_VIEW_NAME = "base-layout";
    private static final String BASE_PAGE_VIEW_NAME = "view";
    protected static final String DEFAULT_MODEL_NAME = "model";

    protected ModelAndView view(String viewName) {
        return this.view(viewName, null);
    }

    protected ModelAndView view(String viewName, Object model) {
        return this.view(viewName, model, DEFAULT_MODEL_NAME);
    }

    protected ModelAndView view(String viewName, Object model, String modelName) {
        ModelAndView modelAndView = new ModelAndView(BASE_PAGE_LAYOUT_VIEW_NAME);
        modelAndView.addObject(modelName, model);
        modelAndView.addObject(BASE_PAGE_VIEW_NAME, viewName);

        return modelAndView;
    }

    protected ModelAndView redirect(String location) {
        return new ModelAndView("redirect:" + location);
    }
}
