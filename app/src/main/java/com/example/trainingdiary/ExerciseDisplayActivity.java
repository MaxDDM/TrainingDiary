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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.trainingdiary.Adapters.SetListAdapter;
import com.example.trainingdiary.Models.Exercise;
import com.example.trainingdiary.Models.Set;

import java.util.ArrayList;

public class ExerciseDisplayActivity extends AppCompatActivity {

    static ArrayList<Set> _sets = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exercise_display);

        Button returnToTrainingButton = findViewById(R.id.returnToTrainingButton);
        TextView nameDisplay = findViewById(R.id.nameTextView);
        ListView listDisplayOfSets = findViewById(R.id.listDisplayOfSets);

        Bundle arguments = getIntent().getExtras();
        int pos = arguments.getInt("positionExercise");
        int posTraining = arguments.getInt("position");
        String date = arguments.getString("date");
        _sets = TrainingDisplayActivity._exercises.get(pos).sets;
        nameDisplay.setText(TrainingDisplayActivity._exercises.get(pos).name);
        SetListAdapter adapter = new SetListAdapter(this, R.layout.activity_set_in_list, _sets);
        listDisplayOfSets.setAdapter(adapter);


        returnToTrainingButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, TrainingDisplayActivity.class);
            intent.putExtra("position", posTraining);
            intent.putExtra("date", date);
            _sets.clear();
            startActivity(intent);
        });

        listDisplayOfSets.setOnItemClickListener((parent, view, position, id_list) -> {
            Intent intent = new Intent(this, SetsDisplayActivity.class);
            intent.putExtra("positionSet", position);
            intent.putExtra("positionExercise", pos);
            intent.putExtra("positionTraining", posTraining);
            intent.putExtra("date", date);
            startActivity(intent);
        });
    }
}