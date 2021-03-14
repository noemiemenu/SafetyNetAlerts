package com.safetynet.alerts.repositories;

import com.safetynet.alerts.model.Person;
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


}
