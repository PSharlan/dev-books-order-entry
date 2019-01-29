package com.netcracker.sharlan.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such Category")
public class CategoryNotFoundException extends RuntimeException{

    public CategoryNotFoundException(long id){
        super("CategoryNotFoundException with id = "+id);
    }

    public CategoryNotFoundException(){
    }
}
