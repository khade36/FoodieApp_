package com.foodie.restaurant.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND,reason = "Restaurant Owner Already Exist")
public class RestaurantOwnerAlreadyExistException extends Exception{
}
