package com.safetynet.alerts.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Date;

@Getter
@Setter
public class PersonInfoFirestationAddress {
    private String firstName;
    private String lastName;
    private String phone;
    private Date birthdate;
    private Collection<String> medications;
    private Collection<String> allergies;

    public PersonInfoFirestationAddress(Person person) {
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.phone = person.getPhone();
    }

    public void setMedicalRecordFields(MedicalRecord medicalRecord) {
        this.birthdate = medicalRecord.getBirthdate();
        this.medications = medicalRecord.getMedications();
        this.allergies = medicalRecord.getAllergies();
    }
}
