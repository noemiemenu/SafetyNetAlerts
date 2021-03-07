package com.safetynet.alerts.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "persons")
public class Person {

    @Id
    @JsonIgnore
    @GeneratedValue
    private Integer id;

    private String firstName;

    private String lastName;

    private String address;

    private String city;

    private String zip;

    private String phone;

    private String email;

    @Transient
    @JsonIgnore
    private boolean isChild;

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                "lastName='" + lastName + '\'' +
                "address='" + address + '\'' +
                "city='" + city + '\'' +
                "zip='" + zip + '\'' +
                "phone='" + phone + '\'' +
                "email='" + email + '\'' +
                '}';
    }
}
