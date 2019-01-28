package com.netcracker.sharlan.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "address")
public class Address extends BaseEntity{

    @Column(name="country", nullable = false)
    private String country;

    @Column(name="city")
    private String city;

    @Column(name="street")
    private String street;

    @Column(name="house_number")
    private int houseNum;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name= "customer_id", nullable = false)
    private Customer customer;

    public Address(String country, String city, String street, int houseNum) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.houseNum = houseNum;
    }

    public Address(String country, String city, String street, int houseNum, Customer customer) {
        this(country, city, street, houseNum);
        this.customer = customer;
    }

    /**
     * case: for frameworks
     */
    public Address(){

    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(int houseNum) {
        this.houseNum = houseNum;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return houseNum == address.houseNum &&
                Objects.equals(country, address.country) &&
                Objects.equals(city, address.city) &&
                Objects.equals(street, address.street) &&
                Objects.equals(customer, address.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, street, houseNum, customer);
    }

    @Override
    public String toString() {
        return "Address{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", houseNum=" + houseNum +
                '}';
    }
}
