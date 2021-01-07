package com.hcl.mobileserviceprovider.service.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllException(Exception e) {
        return new ResponseEntity<>(
                new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> userException(Exception e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MobileServiceProviderException.class)
    public ResponseEntity<ErrorResponse> mobileServiceProviderException(Exception e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST);
    }

}
