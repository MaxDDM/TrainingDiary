package com.example.trainingdiary;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trainingdiary.Adapters.SetListAdapter;
import com.example.trainingdiary.Models.Exercise;
import com.example.trainingdiary.Models.Set;

import java.util.ArrayList;

public class ExerciseActivity extends AppCompatActivity {

    static ArrayList<Set> _sets = new ArrayList<>();
    static private String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exercise);

        Button addSetButton = findViewById(R.id.addSetButton);
        Button finishExerciseButton = findViewById(R.id.finishExerciseButton);
        Button cancelExerciseButton = findViewById(R.id.cancelExerciseButton);
        EditText nameExercise = findViewById(R.id.nameEditText);
        ListView listOfSets = findViewById(R.id.listOfSets);

        int id = TrainingActivity._exercises.size();

        if(!name.isEmpty()) {
            nameExercise.setText(name);
        }

        Bundle arguments = getIntent().getExtras();
        if(arguments != null) {
            if(arguments.containsKey("weight")) {
                float weight = arguments.getFloat("weight");
                int reps = arguments.getInt("reps");
                String comments = arguments.getString("comments");
                int position = arguments.getInt("position");
                _sets.add(position, new Set(weight, reps, comments));
            } else {
                int position = arguments.getInt("position");
                Exercise e = TrainingActivity._exercises.get(position);
                TrainingActivity._exercises.remove(position);
                _sets = e.sets;
                id = position;
            }
            SetListAdapter adapter = new SetListAdapter(this, R.layout.activity_set_in_list, _sets);
            listOfSets.setAdapter(adapter);
        }

        addSetButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, SetActivity.class);
            startActivity(intent);
        });

        int finalId = id;
        finishExerciseButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, TrainingActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("position", finalId);
            name = "";
            startActivity(intent);
        });

        cancelExerciseButton.setOnClickListener(v -> {
            name = "";
            Intent intent = new Intent(this, TrainingActivity.class);
            startActivity(intent);
        });

        nameExercise.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        listOfSets.setOnItemClickListener((parent, view, position, id_list) -> {
            Intent intent = new Intent(this, SetActivity.class);
            intent.putExtra("position", position);
            startActivity(intent);
        });
    }
}