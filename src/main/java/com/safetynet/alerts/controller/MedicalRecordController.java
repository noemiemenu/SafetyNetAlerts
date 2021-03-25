package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.MedicalRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * MedicalRecord Controller
 */
@Api("Gestion des MedicalRecords")
@RestController
@AllArgsConstructor
@RequestMapping("/medicalRecord")
@Slf4j
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;
    private final HttpServletRequest request;

    /**
     * Add medical record response entity.
     *
     * @param medicalRecord the medical record
     * @return the response entity
     */
    @ApiOperation(value = "Ajouter un dossier médical")
    @PostMapping()
    public ResponseEntity addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        log.info("Request to: " + request.getRequestURI(), medicalRecord);
        return medicalRecordService.addMedicalRecord(medicalRecord);
    }

    /**
     * Update medical record medical record.
     *
     * @param medicalRecord the medical record
     * @return the medical record
     */
    @ApiOperation(value = "Mettre à jour un dossier médical")
    @PutMapping()
    public MedicalRecord updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        log.info("Request to: " + request.getRequestURI(), medicalRecord);
        return medicalRecordService.updateMedicalRecord(medicalRecord);
    }

    /**
     * Delete medical record response entity.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @return the response entity
     */
    @ApiOperation(value = "Supprime un dossier médical")
    @DeleteMapping()
    public ResponseEntity deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName) {
        log.info("Request to: " + request.getRequestURI(), firstName, lastName);
        return medicalRecordService.deleteMedicalRecord(firstName, lastName);
    }


}
