package org.softuni.traveltogether.web.controllers;

import org.softuni.traveltogether.repositories.TravelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController extends BaseController {

    @GetMapping("/")
    public ModelAndView index() {
        return super.view("home/index");
    }

    @GetMapping("/home")
    public ModelAndView home() {
        return super.view("home/home");
    }
}
