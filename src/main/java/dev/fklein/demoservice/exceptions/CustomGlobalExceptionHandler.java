package dev.fklein.demoservice.exceptions;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> handleFallback(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("Something went terribly wrong: " + ex.getMessage());
    }

    // Add handler for custom exceptions
    @ExceptionHandler
    public ResponseEntity<Object> handleUserNameNotFoundException(UserNameNotFoundException ex, WebRequest request) {
        CustomErrorDetails customErrorDetails = new CustomErrorDetails(
                new Date(),
                ex.getMessage(),
                request.getDescription(true));
        return ResponseEntity.badRequest().body(customErrorDetails);
    }

    // Override default handling defined in ResponseEntityExceptionHandler
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        CustomErrorDetails customErrorDetails = new CustomErrorDetails(
                new Date(),
                "The data you passed is invalid",
                ex.getMessage());
        return ResponseEntity.badRequest().body(customErrorDetails);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        CustomErrorDetails customErrorDetails = new CustomErrorDetails(
                new Date(),
                "The requested method is not allowed",
                ex.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(customErrorDetails);
    }

    /*
    // Called internally by ResponseEntityExceptionHandler for all default exceptions
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return super.handleExceptionInternal(ex, body, headers, HttpStatus.BANDWIDTH_LIMIT_EXCEEDED, request);
    }
    */
}
