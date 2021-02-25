package com.safetynet.alerts.model;

public class Person {
    int id;
    String firstname;
    String lastname;

    public Person() {
        firstname = "Default firstname";
        lastname = "Default lastname";
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return "Person{}";
    }
}
