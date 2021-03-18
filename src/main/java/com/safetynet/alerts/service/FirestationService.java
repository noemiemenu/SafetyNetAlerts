package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.PersonInfoFirestationAddress;
import com.safetynet.alerts.repositories.FirestationsRepository;
import com.safetynet.alerts.repositories.MedicalRecordsRepository;
import com.safetynet.alerts.repositories.PersonsRepository;
import com.safetynet.alerts.responses.PersonsInFirestationAddressResponse;
import com.safetynet.alerts.responses.PersonsInFirestationNumberResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class FirestationService {

    private final FirestationsRepository firestationsRepository;
    private final PersonsRepository personsRepository;
    private final MedicalRecordsRepository medicalRecordsRepository;
    private final HttpServletRequest request;
    private final PersonService personService;

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

    public Firestation updateFirestation(Firestation firestation) {
        log.info("Reply 200 (OK) to: " + request.getRequestURI(), firestation);
        return firestationsRepository.save(firestation);
    }

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

    public List<String> getPhoneOfPersonToStationNumber(String firestation) {
        List<String> addresses = firestationsRepository.getFirestationsAddressByStation(firestation);
        log.info("Reply 200 (OK) to: " + request.getRequestURI(),firestation);
        return personsRepository.getPeoplePhoneByAddresses(new HashSet<>(addresses));

    }

    public PersonsInFirestationAddressResponse getPeopleByFirestationAddress(String address) {
        List<PersonInfoFirestationAddress> personInfoFirestationAddresses = firestationsRepository.getPeopleByFirestationAddress(address);

        for (PersonInfoFirestationAddress person : personInfoFirestationAddresses) {
            MedicalRecord medicalRecord = medicalRecordsRepository.getMedicalRecordByFirstNameAndLastName(person.getFirstName(), person.getLastName());

            person.setMedicalRecordFields(medicalRecord);
        }

        return new PersonsInFirestationAddressResponse(firestationsRepository.getStationNumberOfFirestationByAddress(address), personInfoFirestationAddresses);
    }
}