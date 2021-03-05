package com.safetynet.alerts.repositories;


import com.safetynet.alerts.model.Firestation;
import org.springframework.data.repository.CrudRepository;

public interface FirestationsRepository extends CrudRepository<Firestation, Integer> {
}
