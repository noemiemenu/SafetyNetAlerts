package com.safetynet.alerts.service;

import com.safetynet.alerts.model.*;
import com.safetynet.alerts.repositories.FirestationsRepository;
import com.safetynet.alerts.repositories.MedicalRecordsRepository;
import com.safetynet.alerts.repositories.PersonsRepository;
import com.safetynet.alerts.responses.ListOfPersonServedByTheseFireStationResponse;
import com.safetynet.alerts.responses.PersonsInFirestationAddressResponse;
import com.safetynet.alerts.responses.PersonsInFirestationNumberResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;

/**
 * The type Firestation service.
 */
@Service
@AllArgsConstructor
@Slf4j
public class FirestationService {

    private final FirestationsRepository firestationsRepository;
    private final PersonsRepository personsRepository;
    private final MedicalRecordsRepository medicalRecordsRepository;
    private final HttpServletRequest request;
    private final PersonService personService;

    /**
     * Gets persons from firestation number.
     *
     * @param stationNumber the station number
     * @return the persons from firestation number
     */
    public PersonsInFirestationNumberResponse getPersonsFromFirestationNumber(String stationNumber) {
        List<String> addresses = firestationsRepository.getFirestationsAddressByStation(stationNumber);
        List<Person> people = personsRepository.getPeopleByAddresses(new HashSet<>(addresses));
        log.info("Request to: " + request.getRequestURI(), stationNumber);

        personService.calculateChild(people);
        long childrenNumber = people.stream().filter(Person::isChild).count();
        long adultsNumber = people.stream().filter(person -> !person.isChild()).count();

        log.info("Reply 200 (OK) to: " + request.getRequestURI(),stationNumber);
        return new PersonsInFirestationNumberResponse(people, childrenNumber, adultsNumber);
    }

    /**
     * Add firestations response entity.
     *
     * @param firestation the firestation
     * @return the response entity
     */
    public ResponseEntity addFirestations(Firestation firestation) {
        Firestation firestationFromDataBase = firestationsRepository.getFirestationsByAddressAndStation(firestation.getAddress(), firestation.getStation());
        if (firestationFromDataBase != null) {
            log.error("Error: firestation already created at: " + request.getRequestURI(), firestation);
            return ResponseEntity.badRequest().body("Firestation already created");
        }
        Firestation savedFirestation = firestationsRepository.save(firestation);
        log.info("Reply 201 (created) to: " + request.getRequestURI(), savedFirestation);
        return ResponseEntity.created(null).body(savedFirestation);
    }

    /**
     * Update firestation firestation.
     *
     * @param firestation the firestation
     * @return the firestation
     */
    public Firestation updateFirestation(Firestation firestation) {
        log.info("Reply 200 (OK) to: " + request.getRequestURI(), firestation);
        return firestationsRepository.save(firestation);
    }

    /**
     * Delete firestation response entity.
     *
     * @param address the address
     * @param station the station
     * @return the response entity
     */
    public ResponseEntity deleteFirestation(String address, String station) {
        Firestation firestation = firestationsRepository.getFirestationsByAddressAndStation(address, station);

        if (firestation == null) {
            log.error("Error: firestation not found: " + request.getRequestURI(), address, station);
            return ResponseEntity.badRequest().body("Firestation not found");
        }
        firestationsRepository.delete(firestation);
        log.info("Reply 200 (OK) to: " + request.getRequestURI(),address, station);
        return ResponseEntity.ok().build();
    }

    /**
     * Gets phone of person to station number.
     *
     * @param firestation the firestation
     * @return the phone of person to station number
     */
    public List<String> getPhoneOfPersonToStationNumber(String firestation) {
        List<String> addresses = firestationsRepository.getFirestationsAddressByStation(firestation);
        log.info("Reply 200 (OK) to: " + request.getRequestURI(),firestation);
        return personsRepository.getPeoplePhoneByAddresses(new HashSet<>(addresses));

    }

    /**
     * Gets list of homes.
     *
     * @param stations the stations
     * @return the list of homes
     */
    public ListOfPersonServedByTheseFireStationResponse getListOfHomes(List<String> stations) {
        List<String> addresses = firestationsRepository.getFirestationsAddressesByStations(new HashSet<>(stations));
        List<PersonInfoWithPhone> peopleList = firestationsRepository.getPeopleByStations(new HashSet<>(addresses));
        setMedicalRecordFieldsToPersonList(peopleList);
        log.info("Reply 200 (OK) to: " + request.getRequestURI(), stations);
        return new ListOfPersonServedByTheseFireStationResponse(peopleList);
    }


    /**
     * Gets people by firestation address.
     *
     * @param address the address
     * @return the people by firestation address
     */
    public PersonsInFirestationAddressResponse getPeopleByFirestationAddress(String address) {
        List<PersonInfoFirestationAddress> personInfoFirestationAddresses = firestationsRepository.getPeopleByFirestationAddress(address);

        setMedicalRecordFieldsToPersonList(personInfoFirestationAddresses);
        log.info("Reply 200 (OK) to: " + request.getRequestURI(),address);
        return new PersonsInFirestationAddressResponse(firestationsRepository.getStationNumberOfFirestationByAddress(address), personInfoFirestationAddresses);
    }

    private <T extends PersonInfoFirestationAddress> void setMedicalRecordFieldsToPersonList(List<T> personList) {
        for (T person : personList) {
            MedicalRecord medicalRecord = medicalRecordsRepository.getMedicalRecordByFirstNameAndLastName(person.getFirstName(), person.getLastName());
            log.debug("Calculating MedicalRecord for " + person.getFirstName());
            person.setMedicalRecordFields(medicalRecord);
        }
    }
}