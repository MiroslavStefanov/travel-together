package org.softuni.traveltogether.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.traveltogether.domain.models.binding.TravelCreateBindingModel;
import org.softuni.traveltogether.domain.models.view.TravelCreateViewModel;
import org.softuni.traveltogether.services.DestinationService;
import org.softuni.traveltogether.services.TravelService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/travels")
public class TravelController extends BaseController {
    private final TravelService travelService;
    private final DestinationService destinationService;
    private final ModelMapper modelMapper;

    public TravelController(TravelService travelService, DestinationService destinationService, ModelMapper modelMapper) {
        this.travelService = travelService;
        this.destinationService = destinationService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/create")
    public ModelAndView create() {
        return super.view("travels/create", new TravelCreateViewModel(
                "create",
                new TravelCreateBindingModel(),
                this.destinationService.getAllDestinationsNames()
        ));
    }

    @PostMapping("/create")
    public ModelAndView createPost(@Valid @ModelAttribute(name = "model") TravelCreateViewModel model, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return super.view("travels/create", model);
        }
        return super.redirect("/travels/" + this.travelService.saveTravel(model.getBindingModel()));
    }

    //rest endpoint get /all?page=

    @GetMapping("")
    public ModelAndView all() {
        return super.view("travels/all");
    }

    // endpoint post /find
    // endpoint get&post /create
    // endpoint get /details
}
