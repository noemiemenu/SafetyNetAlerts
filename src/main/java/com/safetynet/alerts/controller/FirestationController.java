package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Firestation;
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


    @ApiOperation(value = "retourner la liste des caserne de pompiers ")
    @GetMapping(value = "firestations")
    public List<Firestation> firestationList() {
        Firestation firestation = new Firestation();
        ArrayList<Firestation> firestationArrayList = new ArrayList();
        firestationArrayList.add(firestation);
        return firestationArrayList;
    }


}
