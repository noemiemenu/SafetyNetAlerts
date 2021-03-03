package com.safetynet.alerts.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Firestation {

    @Id
    @GeneratedValue
    private int id;

    private String address;
    private String station;

    @Override
    public String toString() {
        return "firestations{" +
                "address='" + address + '\'' +
                "station='" + station + '\'' +
                '}';
    }
}
