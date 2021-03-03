package com.safetynet.alerts.repositories;


import com.safetynet.alerts.model.MedicalRecord;
import org.springframework.data.repository.CrudRepository;

public interface MedicalRecordsRepository extends CrudRepository<MedicalRecord, Integer> {
}
