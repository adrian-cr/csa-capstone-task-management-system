package com.cognizant.TaskManagementSystem.controllers;

import com.cognizant.TaskManagementSystem.dto.ErrorResponse;
import com.cognizant.TaskManagementSystem.exceptions.InvalidRequestException;
import com.cognizant.TaskManagementSystem.exceptions.NotFoundException;
import com.cognizant.TaskManagementSystem.exceptions.ServerErrorException;
import com.cognizant.TaskManagementSystem.exceptions.UnauthorizedAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException() {
        ErrorResponse errorResponse = new ErrorResponse("Sorry! We couldn't find what you're looking for. :(", HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequestException(InvalidRequestException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServerErrorException.class)
    public ResponseEntity<ErrorResponse> handleServerErrorException() {
        ErrorResponse errorResponse = new ErrorResponse("Sorry! We're experiencing some issues. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedAccessException() {
        ErrorResponse errorResponse = new ErrorResponse("Sorry! You don't have access to this resource. :(", HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }
}

