package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repositories.FirestationsRepository;
import com.safetynet.alerts.repositories.MedicalRecordsRepository;
import com.safetynet.alerts.repositories.PersonsRepository;
import com.safetynet.alerts.responses.PersonsInFirestationNumberResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

@Api("Gestion des Firestation ")
@RestController
@AllArgsConstructor
public class FirestationController {

    private final FirestationsRepository firestationsRepository;
    private final PersonsRepository personsRepository;
    private final MedicalRecordsRepository medicalRecordsRepository;

    @ApiOperation(value = "Retourner la liste des caserne de pompiers")
    @GetMapping(value = "firestations")
    public PersonsInFirestationNumberResponse getPersonsFromFirestationNumber(@RequestParam String stationNumber) {
        List<String> addresses = firestationsRepository.getFirestationsByStation(stationNumber);
        List<Person> people = personsRepository.getPeopleByAddresses(new HashSet<>(addresses));

        LocalDateTime now = LocalDateTime.now();

        for (Person person : people) {
            MedicalRecord medicalRecord =
                    medicalRecordsRepository.getMedicalRecordByFirstNameAndLastName(
                            person.getFirstName(),
                            person.getLastName()
                    );

            int year = medicalRecord.getBirthdate().getYear() + 1900;
            int nowYear = now.getYear();

            boolean isChild = nowYear - year <= 18;
            person.setChild(isChild);
        }

        long childrenNumber = people.stream().filter(Person::isChild).count();
        long adultsNumber = people.stream().filter(person -> !person.isChild()).count();

        return new PersonsInFirestationNumberResponse(people, childrenNumber, adultsNumber);
    }


}
