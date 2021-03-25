package com.safetynet.alerts.repositories;


import com.safetynet.alerts.model.MedicalRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Medical records repository.
 */
@Repository
public interface MedicalRecordsRepository extends CrudRepository<MedicalRecord, Integer> {
    /**
     * Gets medical record by first name and last name.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @return the medical record by first name and last name
     */
    MedicalRecord getMedicalRecordByFirstNameAndLastName(String firstName, String lastName);

}
