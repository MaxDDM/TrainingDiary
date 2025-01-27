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
import com.example.trainingdiary.R;

import java.util.List;

public class MonthsListAdapter extends ArrayAdapter<String> {
    LayoutInflater _inflater;
    int _resource;
    List<String> _months;

    public MonthsListAdapter(@NonNull Context context, int resource, @NonNull List<String> months) {
        super(context, resource, months);
        _inflater = LayoutInflater.from(context);
        _resource = resource;
        _months = months;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View mainView = _inflater.inflate(_resource, parent, false);

        TextView monthText = mainView.findViewById(R.id.monthTextView);

        String month = _months.get(position);
        monthText.setText(month);

        return mainView;
    }
}
