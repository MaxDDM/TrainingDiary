package com.example.trainingdiary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalDate;
import java.time.LocalTime;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_menu);

        Button startTrainingButton = findViewById(R.id.startTrainingButton);
        Button archiveButton = findViewById(R.id.archiveButton);
        Button unregisterButton = findViewById(R.id.unregisterButton);

        startTrainingButton.setOnClickListener(v -> {
            LocalDate date;
            LocalTime time;
            date = LocalDate.now();
            time = LocalTime.now();
            Intent intent = new Intent(this, TrainingActivity.class);
            intent.putExtra("Date", date);
            intent.putExtra("Time", time);
            startActivity(intent);
        });

        archiveButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ArchiveActivity.class);
            startActivity(intent);
        });

        unregisterButton.setOnClickListener(v -> {
            ActiveUserInfo.setDefaults("Name", "", MainMenuActivity.this);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }
}