package org.softuni.traveltogether.web.intercpetrors;

import org.modelmapper.ModelMapper;
import org.softuni.traveltogether.domain.models.view.TravelRequestViewModel;
import org.softuni.traveltogether.domain.models.view.UserProfileViewModel;
import org.softuni.traveltogether.specific.UserRequestManager;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

@Component
public class UserRequestInteceptor implements HandlerInterceptor {
    private final UserRequestManager manager;
    private final ModelMapper modelMapper;

    public UserRequestInteceptor(UserRequestManager manager, ModelMapper modelMapper) {
        this.manager = manager;
        this.modelMapper = modelMapper;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if(modelAndView.getModel().containsKey("user")){
            UserProfileViewModel model = (UserProfileViewModel) modelAndView.getModel().get("user");
            if(model != null && this.manager.getRequestCount(model.getUsername()) > 0) {
                model.setRequests(
                        this.manager.getRequests(model.getUsername())
                                .stream()
                                .map(r -> this.modelMapper.map(r, TravelRequestViewModel.class))
                                .collect(Collectors.toSet())
                );
            }
        }
    }
}
