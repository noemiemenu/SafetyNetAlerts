package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repositories.MedicalRecordsRepository;
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

    private final MedicalRecordsRepository medicalRecordsRepository;

    @ApiOperation(value = "Ajouter un dossier médical")
    @PostMapping()
    public ResponseEntity addMedicalRecord(@RequestBody MedicalRecord medicalRecord){
        MedicalRecord medicalRecordFromDataBase = medicalRecordsRepository.getMedicalRecordByFirstNameAndLastName(medicalRecord.getFirstName(),medicalRecord.getLastName());
        if (medicalRecordFromDataBase != null){
            return ResponseEntity.badRequest().body("Medical record already created");
        }
        MedicalRecord savedMedicalRecord = medicalRecordsRepository.save(medicalRecord);
        return ResponseEntity.created(null).body(savedMedicalRecord);
    }

    @ApiOperation(value = "Mettre à jour un dossier médical")
    @PutMapping()
    public MedicalRecord updateMedicalRecord(@RequestBody MedicalRecord medicalRecord){
       return medicalRecordsRepository.save(medicalRecord);
    }

    @ApiOperation(value = "Supprime un dossier médical")
    @DeleteMapping()
    public ResponseEntity deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName){
        MedicalRecord medicalRecord = medicalRecordsRepository.getMedicalRecordByFirstNameAndLastName(firstName, lastName);

        if (medicalRecord == null){
            return ResponseEntity.badRequest().body("medical record not found");
        }
        medicalRecordsRepository.delete(medicalRecord);
        return ResponseEntity.ok().build();
    }


}
