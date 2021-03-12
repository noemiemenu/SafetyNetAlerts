package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api("Gestion des person")
@RestController
@AllArgsConstructor
@RequestMapping("/person")
@Slf4j
public class PersonController {

    private final PersonService personService;

    @ApiOperation(value = "Ajouter d'une Person")
    @PostMapping()
    public ResponseEntity addPerson(@RequestBody Person person) {
        return personService.addPerson(person);
    }

    @ApiOperation(value = "Mettre à jour un person")
    @PutMapping()
    public Person updatePerson(@RequestBody Person person) {
        return personService.updatePerson(person);
    }

    @ApiOperation(value = "Supprime un dossier médical")
    @DeleteMapping()
    public ResponseEntity deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
        return personService.deletePerson(firstName, lastName);
    }

}
