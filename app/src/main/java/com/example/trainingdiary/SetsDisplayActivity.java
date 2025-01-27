package com.example.trainingdiary;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SetsDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sets_display);

        TextView weight = findViewById(R.id.weightDisplayTextView);
        TextView reps = findViewById(R.id.repsDisplayTextView);
        TextView comments = findViewById(R.id.commentsDisplayTextView);
        Button returnToExerciseButton = findViewById(R.id.returnToExerciseButton);

        Bundle arguments = getIntent().getExtras();
        int pos = arguments.getInt("position");
        int posExercise = arguments.getInt("positionExercise");
        int posTraining = arguments.getInt("positionTraining");
        String date = arguments.getString("date");
        weight.setText(String.valueOf(ExerciseDisplayActivity._sets.get(pos).weight));
        reps.setText(String.valueOf(ExerciseDisplayActivity._sets.get(pos).reps));
        comments.setText(String.valueOf(ExerciseDisplayActivity._sets.get(pos).comments));


        returnToExerciseButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ExerciseDisplayActivity.class);
            intent.putExtra("positionExercise", posExercise);
            intent.putExtra("position", posTraining);
            intent.putExtra("date", date);
            startActivity(intent);
        });
    }
}