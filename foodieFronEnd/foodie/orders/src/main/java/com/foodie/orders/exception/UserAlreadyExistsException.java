package com.foodie.orders.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.CONFLICT,reason = "User With this ID Already Exists ")
public class UserAlreadyExistsException extends Exception {
}
