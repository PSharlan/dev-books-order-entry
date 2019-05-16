package com.devbooks.sharlan.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="comment")
public class Comment extends BaseEntity{


    @ManyToOne
    @JoinColumn(name= "offer", nullable = false)
    private Offer offer;

    @Column(name = "message", nullable = false)
    private String message;
    @Column(name = "customer_id", nullable = false)
    private long customerId;
    @Column(name = "editor_customer_id")
    private long editorCustomerId;
    @Column(name = "creation_time", nullable = false)
    private Timestamp creationTime;
    @Column(name = "edition_time")
    private Timestamp editionTime;

}
