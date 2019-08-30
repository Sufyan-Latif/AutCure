package com.example.sufyanlatif.myapplication.models;

public class Child {
    private static Child instance = null;

    String id;
    String firstName, lastName, username, password, address, gender;
    int level;

    public static Child getInstance() {

        if (instance==null)
            instance = new Child();

        return instance;
    }


    private void remove(){
        this.id = null;
        this.firstName = null;
        this.lastName = null;
        this.username = null;
        this.password = null;
    }

    private Child() {
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

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
