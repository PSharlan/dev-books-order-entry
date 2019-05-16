package com.devbooks.sharlan.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class TagDto {

    private Long id;
    @NotEmpty(message="Tag name can not be empty")
    private String name;
}
