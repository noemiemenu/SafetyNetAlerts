package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.responses.ChildrenWithFamilyResponse;
import com.safetynet.alerts.service.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api("Gestion des person")
@RestController
@AllArgsConstructor
@Slf4j
public class PersonController {

    private final PersonService personService;
    private final HttpServletRequest request;

    @ApiOperation(value = "Ajout d'une personne")
    @PostMapping("/person")
    public ResponseEntity addPerson(@RequestBody Person person) {
        log.info("Request to: " + request.getRequestURI(), person);
        return personService.addPerson(person);
    }

    @ApiOperation(value = "Mettre à jour un personne")
    @PutMapping("/person")
    public Person updatePerson(@RequestBody Person person) {
        log.info("Request to: " + request.getRequestURI(), person);
        return personService.updatePerson(person);
    }

    @ApiOperation(value = "Supprime une personne")
    @DeleteMapping("/person")
    public ResponseEntity deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
        log.info("Request to: " + request.getRequestURI(), firstName, lastName);
        return personService.deletePerson(firstName, lastName);
    }

    @ApiOperation(value = "Retourner une liste d'adresse mail")
    @GetMapping("/communityEmail")
    public List<String> getEmailOfAllPersonsInTheCity(@RequestParam String city) {
        log.info("Request to: " + request.getRequestURI(), city);
        return personService.getEmailOfAllPersonsInTheCity(city);
    }

    @ApiOperation(value = "Retourner une liste d'enfants")
    @GetMapping("/childAlert")
    public ChildrenWithFamilyResponse getChildren(@RequestParam String address){
        return personService.getChildren(address);
    }


}
