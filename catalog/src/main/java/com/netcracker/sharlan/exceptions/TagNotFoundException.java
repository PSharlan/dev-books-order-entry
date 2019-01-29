package com.netcracker.sharlan.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such Tag")
public class TagNotFoundException extends RuntimeException{

    public TagNotFoundException(long id){
        super("TagNotFoundException with id = "+id);
    }

    public TagNotFoundException(){
    }
}

