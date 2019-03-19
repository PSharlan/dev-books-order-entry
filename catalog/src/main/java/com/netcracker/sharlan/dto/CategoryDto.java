package com.netcracker.sharlan.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class CategoryDto {

    private Long id;
    @NotEmpty(message="Category name can not be empty")
    private String name;

}
