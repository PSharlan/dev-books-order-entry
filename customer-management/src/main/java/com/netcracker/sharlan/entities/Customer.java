package com.netcracker.sharlan.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "customer")
public class Customer extends BaseEntity{

    @Enumerated(EnumType.ORDINAL)
    @Column(name="role")
    private Role role;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="last_name", nullable = false)
    private String lastName;

    @Column(name="email", nullable = false, unique = true)
    private String email;

    @Column(name="password", nullable = false)
    private String password;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name="date_of_birth")
    private Date dateOfBirth;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "customer", orphanRemoval=true)
    private Set<Address> addresses = new HashSet<>();

    /**
     * case: for frameworks
     */
    public Customer(){

    }


    public Customer(String name, String lastName, String email, String password, Role role, Date dateOfBirth) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.dateOfBirth = dateOfBirth;
    }

    public void addAddress(Address address){
        address.setCustomer(this);
        addresses.add(address);
    }

    public void addAddress(Set<Address> addresses){
        for (Address a : addresses) {
            addAddress(a);
        }
    }

    public void removeAddress(Address address){
        addresses.remove(address);
    }

    public void removeAddress(Set<Address> addresses){
        for (Address a : addresses) {
            removeAddress(a);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }


    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(name, customer.name) &&
                Objects.equals(lastName, customer.lastName) &&
                Objects.equals(email, customer.email) &&
                Objects.equals(role, customer.role) &&
                Objects.equals(dateOfBirth, customer.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lastName, email, role, dateOfBirth);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }

}
