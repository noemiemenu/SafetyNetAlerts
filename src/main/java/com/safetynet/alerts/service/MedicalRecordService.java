package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repositories.MedicalRecordsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Service
@AllArgsConstructor
@Slf4j
public class MedicalRecordService {

    private final MedicalRecordsRepository medicalRecordsRepository;
    private final HttpServletRequest request;


    public ResponseEntity addMedicalRecord(MedicalRecord medicalRecord) {
        MedicalRecord medicalRecordFromDataBase = medicalRecordsRepository.getMedicalRecordByFirstNameAndLastName(medicalRecord.getFirstName(), medicalRecord.getLastName());
        if (medicalRecordFromDataBase != null) {
            log.error("Error: medical record already created at: " + request.getRequestURI(), medicalRecord);
            return ResponseEntity.badRequest().body("Medical record already created");
        }
        MedicalRecord savedMedicalRecord = medicalRecordsRepository.save(medicalRecord);
        log.info("Reply 201 (created) to: " + request.getRequestURI(), medicalRecord);
        return ResponseEntity.created(null).body(savedMedicalRecord);
    }

    public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) {
        log.info("Reply 200 (OK) to: " + request.getRequestURI(), medicalRecord);
        return medicalRecordsRepository.save(medicalRecord);
    }


    public ResponseEntity deleteMedicalRecord(String firstName, String lastName) {

        MedicalRecord medicalRecord = medicalRecordsRepository.getMedicalRecordByFirstNameAndLastName(firstName, lastName);

        if (medicalRecord == null) {
            log.error("Error: medical record not found at: " + request.getRequestURI(), firstName, lastName);
            return ResponseEntity.badRequest().body("Medical record not found");
        }
        medicalRecordsRepository.delete(medicalRecord);
        log.info("Reply 200 (OK) to: " + request.getRequestURI(), firstName, lastName);
        return ResponseEntity.ok().build();
    }
}
