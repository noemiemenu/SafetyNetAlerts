package com.safetynet.alerts.service;


import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.PersonInfo;
import com.safetynet.alerts.repositories.MedicalRecordsRepository;
import com.safetynet.alerts.repositories.PersonsRepository;
import com.safetynet.alerts.responses.ChildrenWithFamilyResponse;
import com.safetynet.alerts.responses.PersonInfoResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
        log.info("Reply 200 (OK) to: " + request.getRequestURI(), city);
        return personsRepository.getPeopleEmailByCity(city);
    }

    public void calculateChild(List<Person> people) {
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

    public ChildrenWithFamilyResponse getChildren(String address) {
        List<Person> people = personsRepository.getPeopleByAddress(address);
        calculateChild(people);
        List<Person> childs = people.stream().filter(Person::isChild).collect(Collectors.toList());
        ChildrenWithFamilyResponse childrenWithFamilyResponse = new ChildrenWithFamilyResponse(new ArrayList<>());
        if (childs.size() == 0) return childrenWithFamilyResponse;

        for (Person child : childs) {
            ChildrenWithFamilyResponse.ChildWithFamily childWithFamily = new ChildrenWithFamilyResponse.ChildWithFamily();
            childWithFamily.setChild(child);
            Date childAge = medicalRecordsRepository.getMedicalRecordByFirstNameAndLastName(child.getFirstName(), child.getLastName()).getBirthdate();
            childWithFamily.setChildAge(childAge);
            List<Person> childFamily = personsRepository.getPeopleByLastNameAndAddressAndCityAndZip(child.getLastName(), child.getAddress(), child.getCity(), child.getZip());

            childFamily = childFamily.stream().filter(person -> !person.getFirstName().equals(child.getFirstName())).collect(Collectors.toList());
            childWithFamily.setFamily(childFamily);

            childrenWithFamilyResponse.getChildren().add(childWithFamily);
        }

        return childrenWithFamilyResponse;
    }


    public PersonInfoResponse getPersonInfo(String firstName, String lastName) {
        List<PersonInfo> personInfos = personsRepository.getPeopleInfoByFirstNameAndLastName(firstName, lastName);
        return new PersonInfoResponse(personInfos);
    }
}
