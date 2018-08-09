package org.softuni.traveltogether.web.controllers;

import org.softuni.traveltogether.domain.models.binding.UserRegisterBindingModel;
import org.softuni.traveltogether.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class UserController extends BaseController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public ModelAndView register() {
        return super.view("user/register", new UserRegisterBindingModel());
    }

    @PostMapping("/register")
    public ModelAndView registerPost(
            @Valid @ModelAttribute("model") UserRegisterBindingModel userRegisterBindingModel,
            BindingResult bindingResult
    ) {
        if(bindingResult.hasErrors()) {
            return super.view("user/register", userRegisterBindingModel, "bindingModel");
        } else {
            this.userService.saveUser(userRegisterBindingModel);
            return super.redirect("/login");
        }
    }

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(required = false, name = "error") String error) {
        return super.view("user/login", error, "error");
    }
}
