package org.softuni.traveltogether.errorHandling;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionController {
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
