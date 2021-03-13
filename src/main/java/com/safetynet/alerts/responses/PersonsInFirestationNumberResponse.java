package com.safetynet.alerts.responses;

import com.safetynet.alerts.model.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PersonsInFirestationNumberResponse {

    private List<Person> persons;
    private long children;
    private long adults;
}