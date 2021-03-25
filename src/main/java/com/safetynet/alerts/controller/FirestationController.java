package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.responses.ListOfPersonServedByTheseFireStationResponse;
import com.safetynet.alerts.responses.PersonsInFirestationAddressResponse;
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

/**
 * FirestationController contains all endpoints concerning a Firestation
 */
@Api("Gestion des Firestation ")
@RestController
@AllArgsConstructor
@Slf4j
public class FirestationController {

    private final FirestationService firestationService;
    private final HttpServletRequest request;

    /**
     * Persons from Firestation number
     *
     * @param stationNumber the station number
     * @return : list of people covered by the fire station and provide a count of the number of adults and the number of children
     */
    @ApiOperation(value = "Retourner une liste des personnes couvertes par la caserne de pompiers est fournir un décompte du nombre d'adultes et du nombre d'enfants")
    @GetMapping("/firestations")
    public PersonsInFirestationNumberResponse getPersonsFromFirestationNumber(@RequestParam String stationNumber) {
        log.info("Request to: " + request.getRequestURI(), stationNumber);
        return firestationService.getPersonsFromFirestationNumber(stationNumber);
    }

    /**
     * Add firestations response entity.
     *
     * @param firestation the firestation
     * @return the response entity
     */
    @ApiOperation(value = "Ajout d'une nouvelle firestation")
    @PostMapping("/firestation")
    public ResponseEntity addFirestations(@RequestBody Firestation firestation) {
        log.info("Request to: " + request.getRequestURI(), firestation);
        return firestationService.addFirestations(firestation);
    }

    /**
     * Update firestation firestation.
     *
     * @param firestation the firestation
     * @return the firestation
     */
    @ApiOperation(value = "Mettre à jour une firestation")
    @PutMapping("/firestation")
    public Firestation updateFirestation(@RequestBody Firestation firestation) {
        log.info("Request to: " + request.getRequestURI(), firestation);
        return firestationService.updateFirestation(firestation);
    }

    /**
     * Delete firestation response entity.
     *
     * @param address the address
     * @param station the station
     * @return the response entity
     */
    @ApiOperation(value = "Supprime une firestation")
    @DeleteMapping("/firestation")
    public ResponseEntity deleteFirestation(@RequestParam String address, @RequestParam String station) {
        log.info("Request to: " + request.getRequestURI(), address, station);
        return firestationService.deleteFirestation(address, station);
    }

    /**
     * Get phone of person to station number list.
     *
     * @param firestation the firestation
     * @return the list
     */
    @ApiOperation(value = "Retourner une liste de Numero de telephone")
    @GetMapping("/phoneAlert")
    public List<String> getPhoneOfPersonToStationNumber(@RequestParam String firestation){
        log.info("Request to: " + request.getRequestURI(), firestation);
        return firestationService.getPhoneOfPersonToStationNumber(firestation);

    }

    /**
     * Get people in firestation address persons in firestation address response.
     *
     * @param address the address
     * @return the persons in firestation address response
     */
    @ApiOperation(value = "retourne la liste des habitants vivant à l’adresse donnée")
    @GetMapping("/fire")
    public PersonsInFirestationAddressResponse getPeopleInFirestationAddress(@RequestParam String address){
        return firestationService.getPeopleByFirestationAddress(address);
    }

    /**
     * Get flood stations list of person served by these fire station response.
     *
     * @param stations the stations
     * @return the list of person served by these fire station response
     */
    @ApiOperation(value = "Retourner une liste de foyers")
    @GetMapping("/flood/stations")
    public ListOfPersonServedByTheseFireStationResponse getFloodStations(@RequestParam List<String> stations){
        return firestationService.getListOfHomes(stations);

    }
}
