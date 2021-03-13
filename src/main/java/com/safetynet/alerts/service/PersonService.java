package com.safetynet.alerts.service;


import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repositories.PersonsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class PersonService {

    private final PersonsRepository personsRepository;
    private final HttpServletRequest request;

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


}
