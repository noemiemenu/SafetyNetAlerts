package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repositories.FirestationsRepository;
import com.safetynet.alerts.repositories.MedicalRecordsRepository;
import com.safetynet.alerts.repositories.PersonsRepository;
import com.safetynet.alerts.responses.PersonsInFirestationNumberResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Api("Gestion des Firestation ")
@RestController
@AllArgsConstructor
@RequestMapping("/firestation")
public class FirestationController {

    private final FirestationsRepository firestationsRepository;
    private final PersonsRepository personsRepository;
    private final MedicalRecordsRepository medicalRecordsRepository;

    @ApiOperation(value = "Retourner une liste des personnes couvertes par la caserne de pompiers est fournir un décompte du nombre d'adultes et du nombre d'enfants")
    @GetMapping()
    public PersonsInFirestationNumberResponse getPersonsFromFirestationNumber(@RequestParam String stationNumber) {
        List<String> addresses = firestationsRepository.getFirestationsByStation(stationNumber);
        List<Person> people = personsRepository.getPeopleByAddresses(new HashSet<>(addresses));

        LocalDateTime now = LocalDateTime.now();

        for (Person person : people) {
            MedicalRecord medicalRecord =
                    medicalRecordsRepository.getMedicalRecordByFirstNameAndLastName(
                            person.getFirstName(),
                            person.getLastName()
                    );

            int year = medicalRecord.getBirthdate().getYear() + 1900;
            int nowYear = now.getYear();

            boolean isChild = nowYear - year <= 18;
            person.setChild(isChild);
        }

        long childrenNumber = people.stream().filter(Person::isChild).count();
        long adultsNumber = people.stream().filter(person -> !person.isChild()).count();

        return new PersonsInFirestationNumberResponse(people, childrenNumber, adultsNumber);
    }

    @ApiOperation(value ="Ajout d'une nouvelle firestation")
    @PostMapping()
    public ResponseEntity addFirestations(@RequestBody Firestation firestation){
        Firestation firestationFromDataBase = firestationsRepository.getFirestationsByStationAndAddress(firestation.getStation(),
                firestation.getAddress());
        if (firestationFromDataBase != null) {
            return ResponseEntity.badRequest().body("Firestation already created");
        }
        Firestation savedFirestation = firestationsRepository.save(firestation);
        return ResponseEntity.created(null).body(savedFirestation);
    }

    @ApiOperation(value = "Mettre à jour une firestation")
    @PutMapping()
    public Firestation updateFirestation (@RequestBody Firestation firestation) {
        return firestationsRepository.save(firestation);
    }

    @ApiOperation(value = "Supprime une firestation")
    @DeleteMapping()
    public ResponseEntity deleteFirestation(@RequestParam String station, @RequestParam String address) {
        Firestation firestation = firestationsRepository.getFirestationsByStationAndAddress(station, address);

        if (firestation == null) {
            return ResponseEntity.badRequest().body("Firestation not found");
        }
        firestationsRepository.delete(firestation);
        return ResponseEntity.ok().build();
    }
}
