package com.devbooks.sharlan.dto;

import com.devbooks.sharlan.entities.Offer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class CommentDto {

    private Long id;
    @NotNull(message="Comment w/o Offer")
    private OfferDto offer;
    @NotEmpty(message="Comment is empty")
    private String message;
    @Min(value = 1, message = "Comment rating less then 1")
    @Max(value = 5, message = "Comment rating more then 5")
    private int rating;
    @NotNull(message="Comment w/o Author")
    private long customerId;
    private long editorCustomerId;
    @NotNull(message="Comment w/o Creation time")
    private Timestamp creationTime;
    private Timestamp editionTime;

}
