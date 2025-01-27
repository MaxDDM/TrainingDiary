package com.example.trainingdiary;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.trainingdiary.Adapters.ExerciseListAdapter;
import com.example.trainingdiary.Adapters.TrainingListAdapter;
import com.example.trainingdiary.Models.Exercise;
import com.example.trainingdiary.Models.Set;
import com.example.trainingdiary.Models.Training;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class TrainingDisplayActivity extends AppCompatActivity {

    static ArrayList<Exercise> _exercises = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_training_display);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://trainingdiary-b09f5-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference table = database.getReference("User");

        Button backToTrainingListButton = findViewById(R.id.backToTrainingListButton);
        ListView listOfTrainingExercises = findViewById(R.id.trainingExercisesListView);

        Bundle arguments = getIntent().getExtras();
        int pos = arguments.getInt("position");
        String date = arguments.getString("date");

        table.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = ActiveUserInfo.getDefaults("Name", TrainingDisplayActivity.this);
                GenericTypeIndicator<ArrayList<Training>> genericTypeIndicator = new GenericTypeIndicator<ArrayList<Training>>() {};
                ArrayList<Training> trainings = snapshot.child(name).child("trainings").getValue(genericTypeIndicator);
                _exercises = trainings.get(pos).exercises;
                ExerciseListAdapter adapter = new ExerciseListAdapter(TrainingDisplayActivity.this, R.layout.activity_exercise_in_list, _exercises);
                listOfTrainingExercises.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TrainingDisplayActivity.this, "Возникла проблема, скорее всего нет соединения с интернетом", Toast.LENGTH_LONG).show();
            }
        });

        backToTrainingListButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MonthTrainingsActivity.class);
            intent.putExtra("date", date);
            _exercises.clear();
            startActivity(intent);
        });

        listOfTrainingExercises.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, ExerciseDisplayActivity.class);
            intent.putExtra("positionExercise", position);
            intent.putExtra("position", pos);
            intent.putExtra("date", date);
            startActivity(intent);
        });
    }
}