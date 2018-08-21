package org.softuni.traveltogether.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.traveltogether.domain.models.binding.TravelCreateBindingModel;
import org.softuni.traveltogether.domain.models.service.TravelServiceModel;
import org.softuni.traveltogether.domain.models.service.UserServiceModel;
import org.softuni.traveltogether.domain.models.view.TravelCreateViewModel;
import org.softuni.traveltogether.domain.models.view.TravelDetailsViewModel;
import org.softuni.traveltogether.services.DestinationService;
import org.softuni.traveltogether.services.TravelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Map;

@Controller
@RequestMapping("/travels")
public class TravelController extends BaseController {
    private static final String TRAVEL_ACTION_AUTHORIZATION_EXPRESSION_STRING =
            "hasRole(T(org.softuni.traveltogether.specific.UserRole).ROLE_ADMIN.name()) " +
                    "|| principal.username.equals(@travelServiceImpl.getTravel(#id).publisher.username)";

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
    public ModelAndView createPost(@Valid @ModelAttribute(DEFAULT_MODEL_NAME) TravelCreateViewModel model, BindingResult bindingResult, Principal principal) {
        if(bindingResult.hasErrors()) {
            model.setAllDestinations(this.destinationService.getAllDestinationsNames());
            return super.view("travels/form", model);
        }
        TravelServiceModel travelServiceModel = TravelServiceModel.instantiateFromBindingModel(model.getBindingModel(), this.modelMapper, principal.getName());
        return super.redirect("/travels/" + this.travelService.saveTravel(travelServiceModel));
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize(TRAVEL_ACTION_AUTHORIZATION_EXPRESSION_STRING)
    public ModelAndView edit(@PathVariable("id") String id) {
        return super.view("travels/form", new TravelCreateViewModel(
                "travels/"+id+"/edit",
                this.modelMapper.map(this.travelService.getTravel(id), TravelCreateBindingModel.class),
                this.destinationService.getAllDestinationsNames()
        ));
    }

    @PostMapping("/{id}/edit")
    @PreAuthorize(TRAVEL_ACTION_AUTHORIZATION_EXPRESSION_STRING)
    public ModelAndView editPost(@Valid @ModelAttribute(DEFAULT_MODEL_NAME) TravelCreateViewModel model, BindingResult bindingResult, @PathVariable("id") String id) {
        if(bindingResult.hasErrors()) {
            model.setAllDestinations(this.destinationService.getAllDestinationsNames());
            return super.view("travels/form", model);
        }
        TravelServiceModel travelServiceModel = this.travelService.getTravel(id);
        this.modelMapper.map(model.getBindingModel(), travelServiceModel);
        return super.redirect("/travels/" + this.travelService.saveTravel(travelServiceModel));
    }

    @GetMapping("/{id}/delete")
    @PreAuthorize(TRAVEL_ACTION_AUTHORIZATION_EXPRESSION_STRING)
    public ModelAndView delete(@PathVariable("id") String id) {
        return super.view("travels/form", new TravelCreateViewModel(
                "travels/"+id+"/delete",
                this.modelMapper.map(this.travelService.getTravel(id), TravelCreateBindingModel.class),
                this.destinationService.getAllDestinationsNames()
        ));
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize(TRAVEL_ACTION_AUTHORIZATION_EXPRESSION_STRING)
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
        return super.view("travels/all", this.destinationService.getAllDestinationsNames());
    }

    @RequestMapping(path = "/{id}/{action}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(TRAVEL_ACTION_AUTHORIZATION_EXPRESSION_STRING + "|| #updates.get('attendantId').toString().equals(principal.getId())")
    public @ResponseBody boolean manageAttendant(@RequestBody Map<String, Object> updates, @PathVariable("id") String id, @PathVariable("action") String action) {
        this.travelService.manageAttendant(id, updates.get("attendantId").toString(), action);
        return true;
    }
}
