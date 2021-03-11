package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.MedicalRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api("Gestion des MedicalRecords")
@RestController
@AllArgsConstructor
@RequestMapping("/medicalRecord")
@Slf4j
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    @ApiOperation(value = "Ajouter un dossier médical")
    @PostMapping()
    public ResponseEntity addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        return medicalRecordService.addMedicalRecord(medicalRecord);
    }

    @ApiOperation(value = "Mettre à jour un dossier médical")
    @PutMapping()
    public MedicalRecord updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        return medicalRecordService.updateMedicalRecord(medicalRecord);
    }

    @ApiOperation(value = "Supprime un dossier médical")
    @DeleteMapping()
    public ResponseEntity deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName) {
        return medicalRecordService.deleteMedicalRecord(firstName, lastName);
    }


}
