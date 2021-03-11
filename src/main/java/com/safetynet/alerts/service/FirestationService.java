package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repositories.FirestationsRepository;
import com.safetynet.alerts.repositories.MedicalRecordsRepository;
import com.safetynet.alerts.repositories.PersonsRepository;
import com.safetynet.alerts.responses.PersonsInFirestationNumberResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
public class FirestationService {

    private final FirestationsRepository firestationsRepository;
    private final PersonsRepository personsRepository;
    private final MedicalRecordsRepository medicalRecordsRepository;

    public PersonsInFirestationNumberResponse getPersonsFromFirestationNumber(String stationNumber) {
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


            person.setChild(nowYear - year <= 18);
        }

        long childrenNumber = people.stream().filter(Person::isChild).count();
        long adultsNumber = people.stream().filter(person -> !person.isChild()).count();

        return new PersonsInFirestationNumberResponse(people, childrenNumber, adultsNumber);
    }

    public ResponseEntity addFirestations(@RequestBody Firestation firestation){
        Firestation firestationFromDataBase = firestationsRepository.getFirestationsByStationAndAddress(firestation.getStation(),
                firestation.getAddress());
        if (firestationFromDataBase != null) {
            return ResponseEntity.badRequest().body("Firestation already created");
        }
        Firestation savedFirestation = firestationsRepository.save(firestation);
        return ResponseEntity.created(null).body(savedFirestation);
    }

    public Firestation updateFirestation(@RequestBody Firestation firestation) {
        return firestationsRepository.save(firestation);
    }

    public ResponseEntity deleteFirestation(@RequestParam String station, @RequestParam String address) {
        Firestation firestation = firestationsRepository.getFirestationsByStationAndAddress(station, address);

        if (firestation == null) {
            return ResponseEntity.badRequest().body("Firestation not found");
        }
        firestationsRepository.delete(firestation);
        return ResponseEntity.ok().build();
    }


}