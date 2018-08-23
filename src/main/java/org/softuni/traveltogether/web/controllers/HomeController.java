package org.softuni.traveltogether.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.traveltogether.domain.models.view.HomeViewModel;
import org.softuni.traveltogether.errorHandling.exceptions.EntityNotFoundException;
import org.softuni.traveltogether.services.UserService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

import static org.softuni.traveltogether.config.WebConstants.HOME_URL;

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

    @GetMapping(HOME_URL)
    public ModelAndView home(Principal principal) {
        throw new EntityNotFoundException("");
        /*return super.view("home/home", this.modelMapper.map(this.userService.findUserByUsername(principal.getName()), HomeViewModel.class));*/
    }
}
