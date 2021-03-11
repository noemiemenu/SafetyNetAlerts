package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.responses.PersonsInFirestationNumberResponse;
import com.safetynet.alerts.service.FirestationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api("Gestion des Firestation ")
@RestController
@AllArgsConstructor
public class FirestationController {

    private final FirestationService firestationService;


    @ApiOperation(value = "Retourner une liste des personnes couvertes par la caserne de pompiers est fournir un décompte du nombre d'adultes et du nombre d'enfants")
    @GetMapping("/firestations")
    public PersonsInFirestationNumberResponse getPersonsFromFirestationNumber(@RequestParam String stationNumber) {
        return firestationService.getPersonsFromFirestationNumber(stationNumber);
    }

    @ApiOperation(value = "Ajout d'une nouvelle firestation")
    @PostMapping("/firestation")
    public ResponseEntity addFirestations(@RequestBody Firestation firestation) {
        return firestationService.addFirestations(firestation);
    }

    @ApiOperation(value = "Mettre à jour une firestation")
    @PutMapping("/firestation")
    public Firestation updateFirestation(@RequestBody Firestation firestation) {
        return firestationService.updateFirestation(firestation);
    }

    @ApiOperation(value = "Supprime une firestation")
    @DeleteMapping("/firestation")
    public ResponseEntity deleteFirestation(@RequestParam String station, @RequestParam String address) {
        return firestationService.deleteFirestation(station, address);
    }
}
