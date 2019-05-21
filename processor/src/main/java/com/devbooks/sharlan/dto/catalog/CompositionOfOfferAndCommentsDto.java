package com.devbooks.sharlan.dto.catalog;

import com.devbooks.sharlan.dto.customer.CustomerDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
public class CompositionOfOfferAndCommentsDto {

    private OfferDto offer;
    private Set<CommentDto> comments;
    private Set<CustomerDto> customers;

}
