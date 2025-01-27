package com.example.trainingdiary.Models;

import java.util.ArrayList;

public class User {
    public String password;
    public ArrayList<Training> trainings = new ArrayList<>();

    public User() {}

    public User(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
