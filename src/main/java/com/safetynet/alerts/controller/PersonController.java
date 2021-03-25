package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.responses.ChildrenWithFamilyResponse;
import com.safetynet.alerts.responses.PersonInfoResponse;
import com.safetynet.alerts.service.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * PersonController
 */
@Api("Gestion des person")
@RestController
@AllArgsConstructor
@Slf4j
public class PersonController {

    private final PersonService personService;
    private final HttpServletRequest request;

    /**
     * Add person response entity.
     *
     * @param person the person
     * @return the response entity
     */
    @ApiOperation(value = "Ajout d'une personne")
    @PostMapping("/person")
    public ResponseEntity addPerson(@RequestBody Person person) {
        log.info("Request to: " + request.getRequestURI(), person);
        return personService.addPerson(person);
    }

    /**
     * Update person person.
     *
     * @param person the person
     * @return the person
     */
    @ApiOperation(value = "Mettre à jour un personne")
    @PutMapping("/person")
    public Person updatePerson(@RequestBody Person person) {
        log.info("Request to: " + request.getRequestURI(), person);
        return personService.updatePerson(person);
    }

    /**
     * Delete person response entity.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @return the response entity
     */
    @ApiOperation(value = "Supprime une personne")
    @DeleteMapping("/person")
    public ResponseEntity deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
        log.info("Request to: " + request.getRequestURI(), firstName, lastName);
        return personService.deletePerson(firstName, lastName);
    }

    /**
     * Gets email of all persons in the city.
     *
     * @param city the city
     * @return the email of all persons in the city
     */
    @ApiOperation(value = "Retourner une liste d'adresse mail")
    @GetMapping("/communityEmail")
    public List<String> getEmailOfAllPersonsInTheCity(@RequestParam String city) {
        log.info("Request to: " + request.getRequestURI(), city);
        return personService.getEmailOfAllPersonsInTheCity(city);
    }

    /**
     * Get children children with family response.
     *
     * @param address the address
     * @return the children with family response
     */
    @ApiOperation(value = "Retourner une liste d'enfants")
    @GetMapping("/childAlert")
    public ChildrenWithFamilyResponse getChildren(@RequestParam String address){
        return personService.getChildren(address);
    }

    /**
     * Gets person info.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @return the person info
     */
    @ApiOperation(value = "Retourner des information sur une personne")
    @GetMapping("/personInfo")
    public PersonInfoResponse getPersonInfo(@RequestParam String firstName, String lastName) {
        log.info("Request to: " + request.getRequestURI(), firstName, lastName);
        return personService.getPersonInfo(firstName, lastName);
    }
}
