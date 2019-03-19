package com.netcracker.sharlan.dto;

import com.netcracker.sharlan.entities.Category;
import com.netcracker.sharlan.entities.Tag;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class OfferDto {

    private Long id;

    @NotEmpty(message="Offer name can not be empty")
    private String name;

    @NotEmpty(message="Description can not be empty")
    private String description;

    @NotNull(message="Category have to be chosen")
    private Category category;

    @Min(value = 1, message = "Price can not be less than 1")
    private double price;

    private Set<Tag> tags = new HashSet<Tag>();
}
