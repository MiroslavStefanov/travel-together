package org.softuni.traveltogether.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.traveltogether.domain.models.binding.TravelCreateBindingModel;
import org.softuni.traveltogether.domain.models.service.TravelServiceModel;
import org.softuni.traveltogether.domain.models.view.TravelCreateViewModel;
import org.softuni.traveltogether.domain.models.view.TravelDetailsViewModel;
import org.softuni.traveltogether.services.DestinationService;
import org.softuni.traveltogether.services.TravelService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        return super.view("travels/form", new TravelCreateViewModel(
                "travels/create",
                new TravelCreateBindingModel(),
                this.destinationService.getAllDestinationsNames()
        ));
    }

    @PostMapping("/create")
    public ModelAndView createPost(@Valid @ModelAttribute(name = "model") TravelCreateViewModel model, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            model.setAllDestinations(this.destinationService.getAllDestinationsNames());
            return super.view("travels/form", model);
        }
        return super.redirect("/travels/" + this.travelService.saveTravel(model.getBindingModel()));
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAnyRole('ROOT', 'ADMIN') || @userServiceImpl.findUserByUsername(principal.username).id.equals(@travelServiceImpl.getTravel(#id).publisher.id)")
    public ModelAndView edit(@PathVariable("id") String id) {
        return super.view("travels/form", new TravelCreateViewModel(
                "travels/"+id+"/edit",
                this.modelMapper.map(this.travelService.getTravel(id), TravelCreateBindingModel.class),
                this.destinationService.getAllDestinationsNames()
        ));
    }

    @PostMapping("/{id}/edit")
    @PreAuthorize("hasAnyRole('ROOT', 'ADMIN') || @userServiceImpl.findUserByUsername(principal.username).id.equals(@travelServiceImpl.getTravel(#id).publisher.id)")
    public ModelAndView editPost(@Valid @ModelAttribute(name = "model") TravelCreateViewModel model, BindingResult bindingResult, @PathVariable("id") String id) {
        if(bindingResult.hasErrors()) {
            model.setAllDestinations(this.destinationService.getAllDestinationsNames());
            return super.view("travels/form", model);
        }
        return super.redirect("/travels/" + this.travelService.editTravel(id, model.getBindingModel()));
    }

    @GetMapping("/{id}/delete")
    @PreAuthorize("hasAnyRole('ROOT', 'ADMIN') || @userServiceImpl.findUserByUsername(principal.username).id.equals(@travelServiceImpl.getTravel(#id).publisher.id)")
    public ModelAndView delete(@PathVariable("id") String id) {
        return super.view("travels/form", new TravelCreateViewModel(
                "travels/"+id+"/delete",
                this.modelMapper.map(this.travelService.getTravel(id), TravelCreateBindingModel.class),
                this.destinationService.getAllDestinationsNames()
        ));
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("hasAnyRole('ROOT', 'ADMIN') || @userServiceImpl.findUserByUsername(principal.username).id.equals(@travelServiceImpl.getTravel(#id).publisher.id)")
    public ModelAndView deletePost(@PathVariable("id") String id) {
        this.travelService.deleteTravel(id);
        return super.redirect("/travels");
    }

    @GetMapping("/{id}")
    public ModelAndView details(@PathVariable("id") String id) {
        return super.view("travels/details", this.modelMapper.map(
                this.travelService.getTravel(id),
                TravelDetailsViewModel.class
        ));
    }

    @GetMapping("")
    public ModelAndView all() {
        return super.view("travels/all",this.destinationService.getAllDestinationsNames());
    }
}
