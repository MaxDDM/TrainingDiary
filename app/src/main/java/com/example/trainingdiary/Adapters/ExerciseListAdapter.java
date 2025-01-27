package com.example.trainingdiary.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.trainingdiary.Models.Exercise;
import com.example.trainingdiary.Models.Set;
import com.example.trainingdiary.R;

import java.util.List;

public class ExerciseListAdapter extends ArrayAdapter<Exercise> {
    LayoutInflater _inflater;
    int _resource;
    List<Exercise> _exercises;

    public ExerciseListAdapter(@NonNull Context context, int resource, @NonNull List<Exercise> exercises) {
        super(context, resource, exercises);
        _inflater = LayoutInflater.from(context);
        _resource = resource;
        _exercises = exercises;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View mainView = _inflater.inflate(_resource, parent, false);

        TextView name = mainView.findViewById(R.id.exerciseNameTextView);
        TextView sets = mainView.findViewById(R.id.setsTextView);

        Exercise exercise = _exercises.get(position);
        name.setText(exercise.name);
        StringBuilder setsText = new StringBuilder();
        for(int i = 0; i < exercise.sets.size(); ++i) {
            if(i != exercise.sets.size() - 1) {
                setsText.append(exercise.sets.get(i).weight).append("☓").append(exercise.sets.get(i).reps).append(", ");
            } else {
                setsText.append(exercise.sets.get(i).weight).append("☓").append(exercise.sets.get(i).reps);
            }
        }
        sets.setText(setsText);

        return mainView;
    }
}
