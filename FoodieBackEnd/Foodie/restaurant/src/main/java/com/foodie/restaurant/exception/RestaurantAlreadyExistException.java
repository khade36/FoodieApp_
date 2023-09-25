package com.foodie.restaurant.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND,reason = "Restaurant Already Exist")
public class RestaurantAlreadyExistException extends Exception {
}
