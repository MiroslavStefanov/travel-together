package org.softuni.traveltogether.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.traveltogether.config.WebConstants;
import org.softuni.traveltogether.domain.models.binding.DestinationBindingModel;
import org.softuni.traveltogether.domain.models.service.DestinationServiceModel;
import org.softuni.traveltogether.domain.models.view.DestinationCreateViewModel;
import org.softuni.traveltogether.domain.models.view.DestinationDetailsViewModel;
import org.softuni.traveltogether.services.DestinationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/destinations")
public class DestinationController extends BaseController {
    private static final String DESTINATION_ACTION_AUTHORIZATION_EXPRESSION_STRING =
            "hasRole(T(org.softuni.traveltogether.specific.UserRole).ROLE_ADMIN.name())";

    private static final long POPULAR_DESTINATION_MIN_TRAVELS = 1;
    
    private final DestinationService destinationService;
    private final ModelMapper modelMapper;

    public DestinationController(DestinationService destinationService, ModelMapper modelMapper) {
        this.destinationService = destinationService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/create")
    public ModelAndView create() {
        return super.view("destination/form", new DestinationCreateViewModel("destinations/create", new DestinationBindingModel()));
    }

    @PostMapping("/create")
    public ModelAndView createPost(@Valid @ModelAttribute(DEFAULT_MODEL_NAME) DestinationCreateViewModel destinationCreateViewModel, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return super.view("destination/form", destinationCreateViewModel);
        } else {
            this.destinationService.saveDestination(this.modelMapper.map(destinationCreateViewModel.getBindingModel(), DestinationServiceModel.class));
            return super.redirect("/destinations/details/" + this.destinationService.saveDestination(
                    this.modelMapper.map(destinationCreateViewModel.getBindingModel(), DestinationServiceModel.class)
            ));
        }
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize(DESTINATION_ACTION_AUTHORIZATION_EXPRESSION_STRING)
    public ModelAndView edit(@PathVariable("id") String id) {
        return super.view("destination/form", new DestinationCreateViewModel(
                "destinations/"+id+"/edit",
                this.modelMapper.map(this.destinationService.getDestination(id), DestinationBindingModel.class)
        ));
    }

    @PostMapping("/{id}/edit")
    @PreAuthorize(DESTINATION_ACTION_AUTHORIZATION_EXPRESSION_STRING)
    public ModelAndView editPost(@Valid @ModelAttribute(DEFAULT_MODEL_NAME) DestinationCreateViewModel model, BindingResult bindingResult, @PathVariable("id") String id) {
        if(bindingResult.hasErrors()) {
            return super.view("destination/form", model);
        }
        DestinationServiceModel destinationServiceModel = this.destinationService.getDestination(id);
        this.modelMapper.map(model.getBindingModel(), destinationServiceModel);
        return super.redirect("/destinations/details/" + this.destinationService.saveDestination(destinationServiceModel));
    }

    @GetMapping("/{id}/delete")
    @PreAuthorize(DESTINATION_ACTION_AUTHORIZATION_EXPRESSION_STRING)
    public ModelAndView delete(@PathVariable("id") String id) {
        return super.view("destination/form", new DestinationCreateViewModel(
                "destinations/"+id+"/delete",
                this.modelMapper.map(this.destinationService.getDestination(id), DestinationBindingModel.class)
        ));
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize(DESTINATION_ACTION_AUTHORIZATION_EXPRESSION_STRING)
    public ModelAndView deletePost(@PathVariable("id") String id) {
        this.destinationService.deleteDestination(id);
        return super.redirect(WebConstants.HOME_URL);
    }

    @GetMapping("/details/{id}")
    public ModelAndView details(@PathVariable("id") String id){
        return super.view("destination/details", this.modelMapper.map(
                this.destinationService.getDestination(id),
                DestinationDetailsViewModel.class
        ));
    }

    @GetMapping("/admin")
    public ModelAndView all() {
        return null;
    }

    @GetMapping("/popular")
    public @ResponseBody List<DestinationDetailsViewModel> popular() {
        return this.destinationService
                .getAllWithMinTravels(POPULAR_DESTINATION_MIN_TRAVELS)
                .stream()
                .map(d -> this.modelMapper.map(d, DestinationDetailsViewModel.class))
                .collect(Collectors.toList());
    }
}
