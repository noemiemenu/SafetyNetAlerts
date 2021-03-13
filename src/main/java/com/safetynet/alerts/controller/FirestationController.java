package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.responses.PersonsInFirestationNumberResponse;
import com.safetynet.alerts.service.FirestationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api("Gestion des Firestation ")
@RestController
@AllArgsConstructor
@Slf4j
public class FirestationController {

    private final FirestationService firestationService;
    private final HttpServletRequest request;


    @ApiOperation(value = "Retourner une liste des personnes couvertes par la caserne de pompiers est fournir un décompte du nombre d'adultes et du nombre d'enfants")
    @GetMapping("/firestations")
    public PersonsInFirestationNumberResponse getPersonsFromFirestationNumber(@RequestParam String stationNumber) {
        log.info("Request to: " + request.getRequestURI(), stationNumber);
        return firestationService.getPersonsFromFirestationNumber(stationNumber);
    }

    @ApiOperation(value = "Ajout d'une nouvelle firestation")
    @PostMapping("/firestation")
    public ResponseEntity addFirestations(@RequestBody Firestation firestation) {
        log.info("Request to: " + request.getRequestURI(), firestation);
        return firestationService.addFirestations(firestation);
    }

    @ApiOperation(value = "Mettre à jour une firestation")
    @PutMapping("/firestation")
    public Firestation updateFirestation(@RequestBody Firestation firestation) {
        log.info("Request to: " + request.getRequestURI(), firestation);
        return firestationService.updateFirestation(firestation);
    }

    @ApiOperation(value = "Supprime une firestation")
    @DeleteMapping("/firestation")
    public ResponseEntity deleteFirestation(@RequestParam String address, @RequestParam String station) {
        log.info("Request to: " + request.getRequestURI(), address, station);
        return firestationService.deleteFirestation(address, station);
    }

    @ApiOperation(value = "Retourner une liste de Numero de telephone")
    @GetMapping("/phoneAlert")
    public List<String> getPhoneOfPersonToStationNumber(@RequestParam String firestation){
        log.info("Request to: " + request.getRequestURI(), firestation);
        return firestationService.getPhoneOfPersonToStationNumber(firestation);

    }
}
