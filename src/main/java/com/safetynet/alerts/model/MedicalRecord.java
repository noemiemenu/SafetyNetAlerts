package com.safetynet.alerts.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="medical_records")
public class MedicalRecord {

    @Id
    @GeneratedValue
    private int id;
    private String firstName;
    private String lastName;
    private Date birthdate;

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
