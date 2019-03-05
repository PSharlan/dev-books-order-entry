package com.netcracker.sharlan.dto.catalog;

import java.util.Objects;

public class OfferDto {

    private Long id;
    private String name;
    private String description;
    private CategoryDto category;
    private double price;

    public OfferDto() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OfferDto)) return false;
        OfferDto offerDto = (OfferDto) o;
        return Double.compare(offerDto.price, price) == 0 &&
                Objects.equals(id, offerDto.id) &&
                Objects.equals(name, offerDto.name) &&
                Objects.equals(description, offerDto.description) &&
                Objects.equals(category, offerDto.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, category, price);
    }

    @Override
    public String toString() {
        return "OfferDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", price=" + price +
                '}';
    }
}
