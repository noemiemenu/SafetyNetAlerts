package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repositories.MedicalRecordsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Medical record service.
 */
@Service
@AllArgsConstructor
@Slf4j
public class MedicalRecordService {

    private final MedicalRecordsRepository medicalRecordsRepository;
    private final HttpServletRequest request;


    /**
     * Add medical record response entity.
     *
     * @param medicalRecord the medical record
     * @return the response entity
     */
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

    /**
     * Update medical record medical record.
     *
     * @param medicalRecord the medical record
     * @return the medical record
     */
    public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) {
        log.info("Reply 200 (OK) to: " + request.getRequestURI(), medicalRecord);
        return medicalRecordsRepository.save(medicalRecord);
    }


    /**
     * Delete medical record response entity.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @return the response entity
     */
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
