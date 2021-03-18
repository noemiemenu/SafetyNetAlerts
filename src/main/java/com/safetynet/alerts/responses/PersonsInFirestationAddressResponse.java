package com.safetynet.alerts.responses;

import com.safetynet.alerts.model.PersonInfoFirestationAddress;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PersonsInFirestationAddressResponse {

    private String stationNumber;
    private List<PersonInfoFirestationAddress> persons;

}