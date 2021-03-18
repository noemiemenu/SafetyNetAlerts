package com.safetynet.alerts.responses;


import com.safetynet.alerts.model.PersonInfoWithPhone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ListOfPersonServedByTheseFireStationResponse {
    private List<PersonInfoWithPhone> people;
}
