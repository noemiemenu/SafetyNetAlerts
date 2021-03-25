package com.safetynet.alerts.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Date;

/**
 * Person info Firestation with Address
 */
@Getter
@Setter
public class PersonInfoFirestationAddress {
    private String firstName;
    private String lastName;
    private String phone;
    private Date birthdate;
    private Collection<String> medications;
    private Collection<String> allergies;

    /**
     * Instantiates a new Person info firestation address.
     *
     * @param person the person
     */
    public PersonInfoFirestationAddress(Person person) {
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.phone = person.getPhone();
    }

    /**
     * Sets medical record fields.
     *
     * @param medicalRecord the medical record
     */
    public void setMedicalRecordFields(MedicalRecord medicalRecord) {
        this.birthdate = medicalRecord.getBirthdate();
        this.medications = medicalRecord.getMedications();
        this.allergies = medicalRecord.getAllergies();
    }
}
