package com.foodie.orders.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND,reason = "Restaurant Owner Not Found")
public class RestoOwnerNotFoundException extends Exception {
}
