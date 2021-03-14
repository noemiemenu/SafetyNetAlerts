package com.safetynet.alerts.responses;

import com.safetynet.alerts.model.PersonInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PersonInfoResponse {
    private List<PersonInfo> people;
}
