package com.example.sufyanlatif.myapplication.models;

import java.io.Serializable;

public class Parent implements Serializable {
    private static Parent instance = null;

    String id;
    String firstName, lastName, username, password, address, gender, associated, phoneNumber, emailAddress;

    public static Parent getInstance() {

        if (instance==null)
            instance = new Parent();

        return instance;
    }


    public void remove(){
        this.id = null;
        this.firstName = null;
        this.lastName = null;
        this.username = null;
        this.password = null;
    }

    public Parent() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAssociated() {
        return this.associated;
    }

    public void setAssociated(String associated) {
        this.associated = associated;
    }

    public String getphoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
