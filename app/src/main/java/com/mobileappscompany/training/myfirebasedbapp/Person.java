package com.mobileappscompany.training.myfirebasedbapp;

/**
 * Created by User on 2/13/2017.
 */

public class Person {

    private String key;
    private String name;
    private String phone;

    public Person() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Person(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
