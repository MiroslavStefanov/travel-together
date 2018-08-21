package org.softuni.traveltogether.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.traveltogether.domain.models.binding.DestinationBindingModel;
import org.softuni.traveltogether.domain.models.service.DestinationServiceModel;
import org.softuni.traveltogether.services.DestinationService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/destinations")
public class DestinationController extends BaseController {
    private final DestinationService destinationService;
    private final ModelMapper modelMapper;

    public DestinationController(DestinationService destinationService, ModelMapper modelMapper) {
        this.destinationService = destinationService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/create")
    public ModelAndView create() {
        return super.view("destination/create", new DestinationBindingModel());
    }

    @PostMapping("/create")
    public ModelAndView createPost(@Valid @ModelAttribute(DEFAULT_MODEL_NAME) DestinationBindingModel destinationBindingModel, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return super.view("destination/create", destinationBindingModel);
        } else {
            this.destinationService.saveDestination(this.modelMapper.map(destinationBindingModel, DestinationServiceModel.class));
            return super.redirect("/travels");
        }
    }
}
