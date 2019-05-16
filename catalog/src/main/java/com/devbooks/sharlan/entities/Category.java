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
 * Represents a Category, providing access to the category id, name and
 * associated offers.
 *
 * @see Offer
 *
 * @author Pavel Sharlan
 * @version  1.0
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="category")
public class Category extends BaseEntity{

    @Column(name="name", nullable = false, unique = true)
    private String name;

    /**
     * The default offers value is a new parameterized HashSet.
     * Note: FetchType.LAZY
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    @JsonIgnore
    private Set<Offer> offers = new HashSet<>();

    public Category(String name){
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
