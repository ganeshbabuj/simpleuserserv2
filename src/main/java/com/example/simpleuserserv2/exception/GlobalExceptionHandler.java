package com.example.simpleuserserv2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public void handleServiceException(HttpServletResponse res, ServiceException ex) throws IOException {
        res.sendError(ex.getHttpStatus().value(), ex.getMessage());
    }
    @ExceptionHandler(NotFoundException.class)
    public void handleNotFoundException(HttpServletResponse res) throws IOException {
        res.sendError(HttpStatus.NOT_FOUND.value(), "Not Found");
    }
    @ExceptionHandler(Exception.class)
    public void handleException(HttpServletResponse res) throws IOException {
        res.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
    }

}
