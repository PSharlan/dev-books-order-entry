package com.devbooks.sharlan.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents an Offer, providing access to the offer's id, name,
 * description, price, category and tags.
 *
 * @see Category
 * @see Tag
 *
 * @author Pavel Sharlan
 * @version  1.0
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="offer")
public class Offer extends BaseEntity{

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name="category", nullable = false)
    private Category category;

    @Column(name="price", nullable = false)
    private double price;

    /**
     * The default tags value is a new parameterized HashSet.
     * Note: FetchType.LAZY
     */
    @JoinTable(
            name = "tag_offer",
            joinColumns = { @JoinColumn(name = "offer_id") },
            inverseJoinColumns = { @JoinColumn(name = "tag_id") }
    )
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "offer")
    @JsonIgnore
    private Set<Comment> comments = new HashSet<>();

    /**
     * Constructs a new Offer with name, description, price, category and tags.
     * Note that you have to add only already existing at Database tags.
     *
     * @param name - offer's name
     * @param description - offer's description
     * @param price - offer's price
     * @param category - offer's category
     * @param tags - offer's tags
     */
    public Offer(String name, String description, double price, Category category, Set<Tag> tags) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return Double.compare(offer.price, price) == 0 &&
                Objects.equals(name, offer.name) &&
                Objects.equals(description, offer.description) &&
                Objects.equals(category, offer.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, category, price);
    }

    @Override
    public String toString() {
        return "Offer{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", price=" + price +
                '}';
    }
}
