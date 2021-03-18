package com.safetynet.alerts.repositories;


import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.PersonInfoFirestationAddress;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FirestationsRepository extends CrudRepository<Firestation, Integer> {

    @Query("select firestation.address from Firestation firestation where firestation.station = :station")
    List<String> getFirestationsAddressByStation(String station);

    Firestation getFirestationsByAddressAndStation(String address, String station);

    @Query("select new com.safetynet.alerts.model.PersonInfoFirestationAddress(person) from Person person, Firestation firestation where firestation.address=:address")
    List<PersonInfoFirestationAddress> getPeopleByFirestationAddress(String address);

    @Query("select firestation.station from Firestation firestation where firestation.address=:address")
    String getStationNumberOfFirestationByAddress(String address);

}
