package com.example.security.Models;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document()
public class User {
    @Id
    private String _id;
    private String name;
    private String lastName;
    private String user;
    private String password;

    @Autowired
    public User(String name, String lastName, String user, String password) {
        this.name = name;
        this.lastName = lastName;
        this.user = user;
        this.password = password;
    }

    public String get_id(){
        return this._id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
