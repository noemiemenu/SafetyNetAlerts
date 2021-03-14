package com.safetynet.alerts.repositories;


import com.safetynet.alerts.model.Firestation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FirestationsRepository extends CrudRepository<Firestation, Integer> {

    @Query("select firestation.address from Firestation firestation where firestation.station = :station")
    List<String> getFirestationsAddressByStation(String station);

    Firestation getFirestationsByAddressAndStation(String address, String station);

}
