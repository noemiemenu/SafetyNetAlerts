package com.safetynet.alerts.responses;

import com.safetynet.alerts.model.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChildrenWithFamilyResponse {
    private List<ChildWithFamily> children;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class ChildWithFamily {
        private Person child;
        private int childAge;
        private List<Person> family;
    }
}
