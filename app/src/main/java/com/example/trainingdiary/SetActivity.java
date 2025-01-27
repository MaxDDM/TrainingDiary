package com.example.trainingdiary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.nfc.FormatException;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class SetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_set);

        EditText weight = findViewById(R.id.weightEditText);
        EditText reps = findViewById(R.id.repsEditText);
        EditText comments = findViewById(R.id.commentsEditText);
        Button addButton = findViewById(R.id.addButton);
        Button cancelSetButton = findViewById(R.id.cancelSetButton);

        int id = ExerciseActivity._sets.size();

        Bundle arguments = getIntent().getExtras();
        if(arguments != null) {
            id = arguments.getInt("position");
            weight.setText(String.valueOf(ExerciseActivity._sets.get(id).weight));
            reps.setText(String.valueOf(ExerciseActivity._sets.get(id).reps));
            comments.setText(String.valueOf(ExerciseActivity._sets.get(id).comments));
            ExerciseActivity._sets.remove(id);
        }

        int finalId = id;
        addButton.setOnClickListener(v -> {
            float weightNumb;
            int repsNumb;
            try {
                weightNumb = Float.parseFloat(weight.getText().toString());
                repsNumb = Integer.parseInt(reps.getText().toString());
            } catch (NullPointerException | NumberFormatException e) {
                addButton.setText("Введено неверное значения для веса или повторений. Введите заново и нажмите");
                return;
            }
            Intent intent = new Intent(this, ExerciseActivity.class);
            intent.putExtra("weight", weightNumb);
            intent.putExtra("reps", repsNumb);
            intent.putExtra("comments", comments.getText().toString());
            intent.putExtra("position", finalId);
            startActivity(intent);
        });

        cancelSetButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ExerciseActivity.class);
            startActivity(intent);
        });
    }
}