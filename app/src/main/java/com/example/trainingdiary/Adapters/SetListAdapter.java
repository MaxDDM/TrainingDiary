package com.example.trainingdiary.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.trainingdiary.Models.Set;
import com.example.trainingdiary.R;

import java.util.List;

public class SetListAdapter extends ArrayAdapter<Set> {
    LayoutInflater _inflater;
    int _resource;
    List<Set> _sets;

    public SetListAdapter(@NonNull Context context, int resource, @NonNull List<Set> sets) {
        super(context, resource, sets);
        _inflater = LayoutInflater.from(context);
        _resource = resource;
        _sets = sets;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View mainView = _inflater.inflate(_resource, parent, false);

        TextView weight = mainView.findViewById(R.id.weightTextView);
        TextView reps = mainView.findViewById(R.id.repsTextView);
        TextView comments = mainView.findViewById(R.id.commentsTextView);

        Set set = _sets.get(position);
        weight.setText(String.valueOf(set.weight));
        reps.setText(String.valueOf(set.reps));
        comments.setText(set.comments);

        return mainView;
    }
}
