package com.safetynet.alerts.repositories;

import com.safetynet.alerts.model.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface PersonsRepository extends CrudRepository<Person, Integer> {
    @Query("select person from Person person where person.address in :addresses")
    List<Person> getPeopleByAddresses(Set<String> addresses);
}
