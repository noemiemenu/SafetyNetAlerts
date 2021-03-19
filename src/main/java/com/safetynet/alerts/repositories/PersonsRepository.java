package com.safetynet.alerts.repositories;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.PersonInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface PersonsRepository extends CrudRepository<Person, Integer> {

    Person getPeopleByFirstNameAndLastName(String firstName, String lastName);

    @Query("select person.email from Person person where person.city = :city")
    List<String> getPeopleEmailByCity(String city);

    @Query("select person from Person person where person.address in :addresses")
    List<Person> getPeopleByAddresses(Set<String> addresses);

    List<Person> getPeopleByAddress(String address);

    @Query("select person.phone from Person person where person.address in :addresses")
    List<String> getPeoplePhoneByAddresses(Set<String> addresses);

    List<Person> getPeopleByLastNameAndAddressAndCityAndZip(String lastName, String address, String city, String zip);

    //where p.ADDRESS='1509 Culver St' and p.FIRST_NAME = mr.FIRST_NAME and p.LAST_NAME = mr.LAST_NAME
    @Query("select new com.safetynet.alerts.model.PersonInfo(person.lastName, person.email, person.address, medicalRecord) from Person person, MedicalRecord medicalRecord where medicalRecord.firstName=:firstName and medicalRecord.lastName=:lastName and person.firstName=:firstName and person.lastName=:lastName")
    List<PersonInfo> getPeopleInfoByFirstNameAndLastName(String firstName, String lastName);
}
