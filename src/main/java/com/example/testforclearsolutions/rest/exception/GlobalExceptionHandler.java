package com.example.testforclearsolutions.rest.exception;

import jakarta.servlet.ServletException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.security.access.AccessDeniedException;

import java.util.stream.Collectors;

@RestControllerAdvice

public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageResponse handleValidationExceptions(MethodArgumentNotValidException e, ServletWebRequest request) {
        final var validationErrors = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> "[%s] - %s".formatted(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.joining(System.lineSeparator(), "Validation error(s): ", ""));

        return new ErrorMessageResponse(validationErrors, request.getRequest().getRequestURI(), request.getHttpMethod().name());
    }

    @ExceptionHandler({BadRequestException.class, ServletException.class, RuntimeException.class, UserYoungException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageResponse handleRuntimeException(RuntimeException e, ServletWebRequest request) {
        if (e instanceof AccessDeniedException accessDeniedException) {
            throw accessDeniedException;
        }
        return logAndGetErrorMessage(request, e);
    }

    private ErrorMessageResponse logAndGetErrorMessage(ServletWebRequest request, Exception e) {
        return logAndGetErrorMessage(request, e.getLocalizedMessage());
    }

    private ErrorMessageResponse logAndGetErrorMessage(ServletWebRequest request, String message) {
        return new ErrorMessageResponse(message, request.getRequest().getRequestURI(), request.getHttpMethod().name());
    }
}
