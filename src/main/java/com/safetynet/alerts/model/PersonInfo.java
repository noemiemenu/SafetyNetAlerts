package com.safetynet.alerts.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Date;

/**
 * Person info
 */
@Getter
@Setter
public class PersonInfo {
    private String lastName;
    private String email;
    private String address;
    private Date birthdate;
    private Collection<String> medications;
    private Collection<String> allergies;

    /**
     * Instantiates a new Person info.
     *
     * @param lastName      the last name
     * @param email         the email
     * @param address       the address
     * @param medicalRecord the medical record
     */
    public PersonInfo(String lastName, String email, String address, MedicalRecord medicalRecord) {
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.birthdate = medicalRecord.getBirthdate();
        this.medications = medicalRecord.getMedications();
        this.allergies = medicalRecord.getAllergies();
    }
}
