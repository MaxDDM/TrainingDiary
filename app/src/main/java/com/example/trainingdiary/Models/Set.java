package com.example.trainingdiary.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Set {
    public float weight;
    public int reps;
    public String comments;
    public Set() {}
    public Set(float weight, int reps, String comments) {
        this.weight = weight;
        this.reps = reps;
        this.comments = comments;
    }
}
