package com.example.trainingdiary.Models;

import java.util.ArrayList;

public class Exercise {
    public String name;
    public ArrayList<Set> sets;
    public Exercise() {}

    public Exercise(String name, ArrayList<Set> sets) {
        this.name = name;
        this.sets = sets;
    }
}
