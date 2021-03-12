package com.safetynet.alerts.service;


import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repositories.PersonsRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@AllArgsConstructor
public class PersonService {

    private final PersonsRepository personsRepository;

    public ResponseEntity addPerson(@RequestBody Person person) {
        Person personFromDataBase = personsRepository.getPeopleByFirstNameAndLastName(person.getFirstName(), person.getLastName());
        if (personFromDataBase != null) {
            return ResponseEntity.badRequest().body("Person already created");
        }
        Person savedPerson = personsRepository.save(person);
        return ResponseEntity.created(null).body(savedPerson);
    }

    public Person updatePerson(@RequestBody Person person) {
        return personsRepository.save(person);
    }

    public ResponseEntity deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
        Person person = personsRepository.getPeopleByFirstNameAndLastName(firstName, lastName);

        if (person == null) {
            return ResponseEntity.badRequest().body("Person not found");
        }
        personsRepository.delete(person);
        return ResponseEntity.ok().build();
    }


}
