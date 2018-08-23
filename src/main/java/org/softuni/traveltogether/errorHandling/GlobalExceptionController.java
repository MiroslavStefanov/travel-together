package org.softuni.traveltogether.errorHandling;

import org.modelmapper.ModelMapper;
import org.softuni.traveltogether.config.WebConstants;
import org.softuni.traveltogether.domain.models.binding.UserRegisterBindingModel;
import org.softuni.traveltogether.errorHandling.exceptions.EntityNotFoundException;
import org.softuni.traveltogether.errorHandling.exceptions.UserEmailAlreadyUsedException;
import org.softuni.traveltogether.errorHandling.exceptions.UserException;
import org.softuni.traveltogether.errorHandling.exceptions.UsernameAlreadyUsedException;
import org.softuni.traveltogether.web.controllers.BaseController;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionController extends BaseController {
    private final ModelMapper modelMapper;

    public GlobalExceptionController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @ExceptionHandler(UserException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ModelAndView duplicateUsername(UserException e) {
        UserRegisterBindingModel model = this.modelMapper.map(e.getServiceModel(), UserRegisterBindingModel.class);
        BindingResult bindingResult = new BeanPropertyBindingResult(model, DEFAULT_MODEL_NAME);

        if(e instanceof UserEmailAlreadyUsedException)
            bindingResult.rejectValue("email", "", e.getMessage());
        else if(e instanceof UsernameAlreadyUsedException)
            bindingResult.rejectValue("username", "", e.getMessage());

        return super.view("user/register", model, DEFAULT_MODEL_NAME, bindingResult);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView accessDenied(AccessDeniedException e) {
        return super.page("error/error-403");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView notFount(EntityNotFoundException e) {
        return super.page("error/error-404");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView getException(Exception e){
        e.printStackTrace();
        return super.page("error/error-500");
    }
}
