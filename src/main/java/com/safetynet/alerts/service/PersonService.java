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

/**
 * The type Person service.
 */
@Service
@AllArgsConstructor
@Slf4j
public class PersonService {

    private final PersonsRepository personsRepository;
    private final HttpServletRequest request;
    private final MedicalRecordsRepository medicalRecordsRepository;

    /**
     * Add person response entity.
     *
     * @param person the person
     * @return the response entity
     */
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

    /**
     * Update person person.
     *
     * @param person the person
     * @return the person
     */
    public Person updatePerson(Person person) {
        log.info("Reply 200 (OK) to: " + request.getRequestURI(), person);
        return personsRepository.save(person);
    }

    /**
     * Delete person response entity.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @return the response entity
     */
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

    /**
     * Gets email of all persons in the city.
     *
     * @param city the city
     * @return the email of all persons in the city
     */
    public List<String> getEmailOfAllPersonsInTheCity(String city) {
        log.info("Reply 200 (OK) to: " + request.getRequestURI(), city);
        return personsRepository.getPeopleEmailByCity(city);
    }

    /**
     * Calculate child.
     *
     * @param people the people
     */
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

    /**
     * Gets children.
     *
     * @param address the address
     * @return the children
     */
    public ChildrenWithFamilyResponse getChildren(String address) {
        List<Person> people = personsRepository.getPeopleByAddress(address);
        calculateChild(people);
        List<Person> childs = people.stream().filter(Person::isChild).collect(Collectors.toList());
        ChildrenWithFamilyResponse childrenWithFamilyResponse = new ChildrenWithFamilyResponse(new ArrayList<>());
        if (childs.size() == 0) return childrenWithFamilyResponse;

        LocalDateTime now = LocalDateTime.now();

        for (Person child : childs) {
            ChildrenWithFamilyResponse.ChildWithFamily childWithFamily = new ChildrenWithFamilyResponse.ChildWithFamily();
            childWithFamily.setChild(child);
            Date childAge = medicalRecordsRepository.getMedicalRecordByFirstNameAndLastName(child.getFirstName(), child.getLastName()).getBirthdate();
            int childAgeInteger = childAge.getYear() + 1900;
            int nowYear = now.getYear();
            childWithFamily.setChildAge(nowYear - childAgeInteger);
            List<Person> childFamily = personsRepository.getPeopleByLastNameAndAddressAndCityAndZip(child.getLastName(), child.getAddress(), child.getCity(), child.getZip());

            childFamily = childFamily.stream().filter(person -> !person.getFirstName().equals(child.getFirstName())).collect(Collectors.toList());
            childWithFamily.setFamily(childFamily);

            childrenWithFamilyResponse.getChildren().add(childWithFamily);
        }

        return childrenWithFamilyResponse;
    }


    /**
     * Gets person info.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @return the person info
     */
    public PersonInfoResponse getPersonInfo(String firstName, String lastName) {
        List<PersonInfo> personInfos = personsRepository.getPeopleInfoByFirstNameAndLastName(firstName, lastName);
        return new PersonInfoResponse(personInfos);
    }
}
