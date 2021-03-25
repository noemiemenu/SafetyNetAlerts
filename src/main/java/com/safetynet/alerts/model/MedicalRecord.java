package com.safetynet.alerts.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The type Medical record.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "medical_records")
public class MedicalRecord {

    /**
     * The constant DATE_FORMAT.
     */
    public static final String DATE_FORMAT = "dd/MM/yyyy";

    @Id
    @GeneratedValue
    private int id;

    private String firstName;
    private String lastName;

    @ApiModelProperty(required = true, example = "30/12/2020")
    @JsonFormat(pattern = DATE_FORMAT)
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
