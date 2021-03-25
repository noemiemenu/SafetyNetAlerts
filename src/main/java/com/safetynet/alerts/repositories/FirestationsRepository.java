package com.safetynet.alerts.repositories;


import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.PersonInfoFirestationAddress;
import com.safetynet.alerts.model.PersonInfoWithPhone;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * The interface Firestations repository.
 */
@Repository
public interface FirestationsRepository extends CrudRepository<Firestation, Integer> {

    /**
     * Gets firestations address by station.
     *
     * @param station the station
     * @return the firestations address by station
     */
    @Query("select firestation.address from Firestation firestation where firestation.station = :station")
    List<String> getFirestationsAddressByStation(String station);

    /**
     * Gets firestations addresses by stations.
     *
     * @param stations the stations
     * @return the firestations addresses by stations
     */
    @Query("select firestation.address from Firestation firestation where firestation.station in :stations")
    List<String> getFirestationsAddressesByStations(Set<String> stations);

    /**
     * Gets firestations by address and station.
     *
     * @param address the address
     * @param station the station
     * @return the firestations by address and station
     */
    Firestation getFirestationsByAddressAndStation(String address, String station);

    /**
     * Gets people by stations.
     *
     * @param addresses the addresses
     * @return the people by stations
     */
    @Query("select new com.safetynet.alerts.model.PersonInfoWithPhone(person) from Person person where person.address in :addresses")
    List<PersonInfoWithPhone> getPeopleByStations(Set<String> addresses);

    /**
     * Gets people by firestation address.
     *
     * @param address the address
     * @return the people by firestation address
     */
    @Query("select new com.safetynet.alerts.model.PersonInfoFirestationAddress(person) from Person person, Firestation firestation where firestation.address=:address")
    List<PersonInfoFirestationAddress> getPeopleByFirestationAddress(String address);

    /**
     * Gets station number of firestation by address.
     *
     * @param address the address
     * @return the station number of firestation by address
     */
    @Query("select firestation.station from Firestation firestation where firestation.address=:address")
    String getStationNumberOfFirestationByAddress(String address);

}
