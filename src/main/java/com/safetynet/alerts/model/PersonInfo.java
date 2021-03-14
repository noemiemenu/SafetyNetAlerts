package com.safetynet.alerts.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Date;

@Getter
@Setter
public class PersonInfo {
    private String lastName;
    private String email;
    private String address;
    private Date birthdate;
    private Collection<String> medications;
    private Collection<String> allergies;

    public PersonInfo(String lastName, String email, String address, MedicalRecord medicalRecord) {
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.birthdate = medicalRecord.getBirthdate();
        this.medications = medicalRecord.getMedications();
        this.allergies = medicalRecord.getAllergies();
    }
}
