package org.softuni.traveltogether.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.traveltogether.domain.models.view.HomeViewModel;
import org.softuni.traveltogether.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class HomeController extends BaseController {
    private final ModelMapper modelMapper;
    private final UserService userService;

    public HomeController(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @GetMapping("/")
    public ModelAndView index() {
        return super.view("home/index");
    }

    @GetMapping("/home")
    public ModelAndView home(Principal principal) {
        return super.view("home/home", this.modelMapper.map(this.userService.findUserByUsername(principal.getName()), HomeViewModel.class));
    }
}
