package com.springboot.microservices.Exception;


import com.springboot.microservices.DTO.Error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class GlobalExceptionHandler {


    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //400 Error Bad Request
    @ExceptionHandler({InvalidDataException.class, HttpMessageNotReadableException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleInvalidDataException(Throwable throwable){
        log.warn(throwable.getMessage());
        return new Error(HttpStatus.BAD_REQUEST.getReasonPhrase(), throwable.getMessage());
    }

    //404 Error Not Found
    @ExceptionHandler(DataNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleDataNotFoundException(DataNotFoundException exception){
        log.warn(exception.getMessage());
        return new Error(HttpStatus.NOT_FOUND.getReasonPhrase(), exception.getMessage());
    }



    //500 Error Internal Server error(General Error other than 2)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error handleUnknownException(Exception e){
        log.error(e.getMessage());
        return new Error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage());
    }
}