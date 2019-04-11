package com.example.ivykiai.models;

public class User {

    private int id;
    private String email, name, last_name, phone_number, personal_code;

    public User(int id, String email, String name, String last_name, String phone_number, String personal_code) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.last_name = last_name;
        this.phone_number = phone_number;
        this.personal_code = personal_code;

    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getLast_Name() {
        return last_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getPersonal_code() {
        return personal_code;
    }
}