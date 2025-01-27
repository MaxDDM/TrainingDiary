package com.example.trainingdiary.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.trainingdiary.Models.Set;
import com.example.trainingdiary.Models.Training;
import com.example.trainingdiary.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TrainingListAdapter extends ArrayAdapter<Training> {
    LayoutInflater _inflater;
    int _resource;
    List<Training> _trainings;

    public TrainingListAdapter(@NonNull Context context, int resource, @NonNull List<Training> trainings) {
        super(context, resource, trainings);
        _inflater = LayoutInflater.from(context);
        _resource = resource;
        _trainings = trainings;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View mainView = _inflater.inflate(_resource, parent, false);

        TextView trainingTextView = mainView.findViewById(R.id.trainingTextView);

        Training training = _trainings.get(position);
        trainingTextView.setText(training.date);

        return mainView;
    }
}
