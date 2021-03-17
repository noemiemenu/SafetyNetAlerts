package com.safetynet.alerts.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Date;

@Getter
@Setter
public class PersonInfoWithPhone {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String address;
    private Date birthdate;
    private Collection<String> medications;
    private Collection<String> allergies;

    public PersonInfoWithPhone(String firstName, String lastName, String phone, String email, String address, MedicalRecord medicalRecord) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.birthdate = medicalRecord.getBirthdate();
        this.medications = medicalRecord.getMedications();
        this.allergies = medicalRecord.getAllergies();
    }
}
