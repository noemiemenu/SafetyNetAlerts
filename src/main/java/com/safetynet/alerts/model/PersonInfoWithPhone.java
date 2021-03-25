package com.safetynet.alerts.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Person info with Phone
 */
@Getter
@Setter
public class PersonInfoWithPhone extends PersonInfoFirestationAddress {
    private String email;
    private String address;

    /**
     * Instantiates a new Person info with phone.
     *
     * @param person the person
     */
    public PersonInfoWithPhone(Person person) {
        super(person);
        this.email = person.getEmail();
        this.address = person.getAddress();
    }
}
