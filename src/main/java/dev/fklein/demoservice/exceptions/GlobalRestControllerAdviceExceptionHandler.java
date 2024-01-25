package dev.fklein.demoservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class GlobalRestControllerAdviceExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomErrorDetails handleUserNameNotFoundException(UserNameNotFoundException ex, WebRequest request) {
        CustomErrorDetails customErrorDetails = new CustomErrorDetails(
                new Date(),
                "RestHandler: " + ex.getMessage(),
                request.getDescription(false));
        return customErrorDetails;
    }
}
