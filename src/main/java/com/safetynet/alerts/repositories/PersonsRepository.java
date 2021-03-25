package com.safetynet.alerts.repositories;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.PersonInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

/**
 * PersonsRepository
 */
public interface PersonsRepository extends CrudRepository<Person, Integer> {

    /**
     * Gets people by first name and last name.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @return the people by first name and last name
     */
    Person getPeopleByFirstNameAndLastName(String firstName, String lastName);

    /**
     * Gets people email by city.
     *
     * @param city the city
     * @return the people email by city
     */
    @Query("select person.email from Person person where person.city = :city")
    List<String> getPeopleEmailByCity(String city);

    /**
     * Gets people by addresses.
     *
     * @param addresses the addresses
     * @return the people by addresses
     */
    @Query("select person from Person person where person.address in :addresses")
    List<Person> getPeopleByAddresses(Set<String> addresses);

    /**
     * Gets people by address.
     *
     * @param address the address
     * @return the people by address
     */
    List<Person> getPeopleByAddress(String address);

    /**
     * Gets people phone by addresses.
     *
     * @param addresses the addresses
     * @return the people phone by addresses
     */
    @Query("select person.phone from Person person where person.address in :addresses")
    List<String> getPeoplePhoneByAddresses(Set<String> addresses);

    /**
     * Gets people by last name and address and city and zip.
     *
     * @param lastName the last name
     * @param address  the address
     * @param city     the city
     * @param zip      the zip
     * @return the people by last name and address and city and zip
     */
    List<Person> getPeopleByLastNameAndAddressAndCityAndZip(String lastName, String address, String city, String zip);

    /**
     * Gets people info by first name and last name.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @return the people info by first name and last name
     */
//where p.ADDRESS='1509 Culver St' and p.FIRST_NAME = mr.FIRST_NAME and p.LAST_NAME = mr.LAST_NAME
    @Query("select new com.safetynet.alerts.model.PersonInfo(person.lastName, person.email, person.address, medicalRecord) from Person person, MedicalRecord medicalRecord where medicalRecord.firstName=:firstName and medicalRecord.lastName=:lastName and person.firstName=:firstName and person.lastName=:lastName")
    List<PersonInfo> getPeopleInfoByFirstNameAndLastName(String firstName, String lastName);
}
