package com.safetynet.alerts.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MedicalRecord {

    @Id
    @GeneratedValue
    private int id;
    private String firstName;
    private String lastName;
    private String birthdate;

    @ElementCollection
    private List<String> medications;

    @ElementCollection
    private List<String> allergies;


    @Override
    public String toString() {
        return "medicalrecords{" +
                "firstName='" + firstName + '\'' +
                "lastName='" + lastName + '\'' +
                "birthdate='" + birthdate + '\'' +
                "medications='" + medications + '\'' +
                "allergies='" + allergies + '\'' +
                '}';
    }
}
