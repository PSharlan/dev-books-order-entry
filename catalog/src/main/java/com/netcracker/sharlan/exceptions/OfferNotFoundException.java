package com.netcracker.sharlan.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such Offer")
public class OfferNotFoundException extends RuntimeException{

    public OfferNotFoundException(long id){
        super("OfferNotFoundException with id = "+id);
    }

    public OfferNotFoundException(){
    }
}
