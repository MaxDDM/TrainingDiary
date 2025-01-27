package com.example.trainingdiary.Models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Training {
    public String date;
    public String time;
    public ArrayList<Exercise> exercises = new ArrayList<>();

    public Training() {
    }

    public Training(String date, String time, ArrayList<Exercise> exercises) {
        this.date = date;
        this.time = time;
        this.exercises = exercises;
    }
}
