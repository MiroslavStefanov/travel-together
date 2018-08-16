package org.softuni.traveltogether.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.traveltogether.domain.models.binding.UserRegisterBindingModel;
import org.softuni.traveltogether.domain.models.view.UserProfileViewModel;
import org.softuni.traveltogether.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class UserController extends BaseController {
    private final ModelMapper modelMapper;
    private final UserService userService;

    public UserController(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
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

    @GetMapping("/profile/{username}")
    public ModelAndView profile(@PathVariable("username") String username) {
        return super.view("user/profile", this.modelMapper.map(this.userService.findUserByUsername(username), UserProfileViewModel.class), "user");
    }

    @GetMapping("/profile/${username}/edit")
    @PreAuthorize("#username.equals(principal.username)")
    public ModelAndView editProfile(@PathVariable("username") String username) {
        return super.view("user/edit");
    }
}
