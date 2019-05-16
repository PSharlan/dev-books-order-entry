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
 * Represents a Tag, providing access to the category id, name and
 * associated offers.
 *
 * @see BaseEntity
 * @see Offer
 *
 * @author Pavel Sharlan
 * @version  1.0
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tag")
public class Tag extends BaseEntity {

    @Column(name="name", nullable = false, unique = true)
    private String name;

    /**
     * The default offers value is a new parameterized HashSet.
     * Note: FetchType.LAZY
     */
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags")
    private Set<Offer> offers = new HashSet<Offer>();

    public Tag(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
