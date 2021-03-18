package com.safetynet.alerts.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonInfoWithPhone extends PersonInfoFirestationAddress {
    private String email;
    private String address;

    public PersonInfoWithPhone(Person person) {
        super(person);
        this.email = person.getEmail();
        this.address = person.getAddress();
    }
}
