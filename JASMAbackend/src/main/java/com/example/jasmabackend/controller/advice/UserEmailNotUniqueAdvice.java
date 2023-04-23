package com.example.jasmabackend.controller.advice;

import com.example.jasmabackend.exceptions.UserEmailNotUniqueException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UserEmailNotUniqueAdvice {

    @ResponseBody
    @ExceptionHandler(UserEmailNotUniqueException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String userEmailNotUniqueHandler(UserEmailNotUniqueException ex) {
        return ex.getMessage();
    }
}
