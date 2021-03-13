package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repositories.MedicalRecordsRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@AllArgsConstructor
public class MedicalRecordService {

    private final MedicalRecordsRepository medicalRecordsRepository;


    public ResponseEntity addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        MedicalRecord medicalRecordFromDataBase = medicalRecordsRepository.getMedicalRecordByFirstNameAndLastName(medicalRecord.getFirstName(), medicalRecord.getLastName());
        if (medicalRecordFromDataBase != null) {
            return ResponseEntity.badRequest().body("Medical record already created");
        }
        MedicalRecord savedMedicalRecord = medicalRecordsRepository.save(medicalRecord);
        return ResponseEntity.created(null).body(savedMedicalRecord);
    }

    public MedicalRecord updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        return medicalRecordsRepository.save(medicalRecord);
    }


    public ResponseEntity deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName) {
        MedicalRecord medicalRecord = medicalRecordsRepository.getMedicalRecordByFirstNameAndLastName(firstName, lastName);

        if (medicalRecord == null) {
            return ResponseEntity.badRequest().body("medical record not found");
        }
        medicalRecordsRepository.delete(medicalRecord);
        return ResponseEntity.ok().build();
    }
}
