package org.softuni.traveltogether.errorHandling;

import org.softuni.traveltogether.errorHandling.exceptions.TravelException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(TravelException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleTravelException(TravelException e) {
        return String.format("<h1 class=\"text-danger\">%s</h1>", e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String getException(Exception e){
        e.printStackTrace();
        return String.format(
                "<h1>%s</h1>" +
                "<h3>%s</h3>" +
                "<p>%s</p>", e.getCause(), e.getMessage(), String.join(
                        "\r\n",
                        Arrays.stream(e.getStackTrace())
                                .map(StackTraceElement::toString)
                                .collect(Collectors.toList())
                ));
    }
}
