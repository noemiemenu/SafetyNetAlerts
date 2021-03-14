package com.safetynet.alerts.service;


import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repositories.MedicalRecordsRepository;
import com.safetynet.alerts.repositories.PersonsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class PersonService {

    private final PersonsRepository personsRepository;
    private final HttpServletRequest request;
    private final MedicalRecordsRepository medicalRecordsRepository;

    public ResponseEntity addPerson(Person person) {
        Person personFromDataBase = personsRepository.getPeopleByFirstNameAndLastName(person.getFirstName(), person.getLastName());
        if (personFromDataBase != null) {
            log.error("Error: person already created at: " + request.getRequestURI(), person);
            return ResponseEntity.badRequest().body("Person already created");
        }
        Person savedPerson = personsRepository.save(person);
        log.info("Reply 201 (created) to: " + request.getRequestURI(), savedPerson);
        return ResponseEntity.created(null).body(savedPerson);
    }

    public Person updatePerson(Person person) {
        log.info("Reply 200 (OK) to: " + request.getRequestURI(), person);
        return personsRepository.save(person);
    }

    public ResponseEntity deletePerson(String firstName, String lastName) {
        Person person = personsRepository.getPeopleByFirstNameAndLastName(firstName, lastName);

        if (person == null) {
            log.error("Error: person not found: " + request.getRequestURI(), firstName, lastName);
            return ResponseEntity.badRequest().body("Person not found");
        }
        personsRepository.delete(person);
        log.info("Reply 200 (OK) to: " + request.getRequestURI(), firstName, lastName);
        return ResponseEntity.ok().build();
    }

    public List<String> getEmailOfAllPersonsInTheCity(String city) {
        log.info("Reply 200 (OK) to: " + request.getRequestURI(),city);
        return personsRepository.getPeopleEmailByCity(city);
    }

    public void calculateChild(List<Person> people){
        LocalDateTime now = LocalDateTime.now();

        for (Person person : people) {
            MedicalRecord medicalRecord =
                    medicalRecordsRepository.getMedicalRecordByFirstNameAndLastName(
                            person.getFirstName(),
                            person.getLastName()
                    );

            log.debug("Sort by date of birth: " + request.getRequestURI(), person);
            int year = medicalRecord.getBirthdate().getYear() + 1900;
            int nowYear = now.getYear();
            person.setChild(nowYear - year <= 18);
        }
    }

    public List<Person> getChild(String address) {
        List<Person> people = personsRepository.getPeopleByAddress(address);
        calculateChild(people);
        return people.stream().filter(Person::isChild).collect(Collectors.toList());
    }
}
