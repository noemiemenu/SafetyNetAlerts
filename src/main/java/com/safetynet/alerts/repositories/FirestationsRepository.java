package com.safetynet.alerts.repositories;


import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FirestationsRepository extends CrudRepository<Firestation, Integer> {

    @Query("select firestation.address from Firestation firestation where firestation.station = :station")
    List<String> getFirestationsByStation(String station);
}
