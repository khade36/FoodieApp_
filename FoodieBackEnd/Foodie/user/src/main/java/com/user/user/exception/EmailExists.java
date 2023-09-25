package com.user.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND,reason = "Email Exist")
public class EmailExists extends Exception{
    public void getMessage(String expextion) {
    }
}