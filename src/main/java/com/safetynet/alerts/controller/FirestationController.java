package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Person;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api("Gestion des Firestation ")
@RestController
public class FirestationController {

    // Applications de la table Application
    @ApiOperation(value = "retourner une liste des personnes couvertes par la caserne de pompiers correspondante ")
    @GetMapping(value = "firestation")
    public List<Person> listePlanAction() {
        Person person = new Person();
        ArrayList<Person> listPerson = new ArrayList();
        listPerson.add(person);
        return listPerson;
    }


}
