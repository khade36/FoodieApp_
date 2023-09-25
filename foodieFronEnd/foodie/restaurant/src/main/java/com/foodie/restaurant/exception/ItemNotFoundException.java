package com.foodie.restaurant.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND,reason = "Item  does not Exist")
public class ItemNotFoundException extends Exception{
}
