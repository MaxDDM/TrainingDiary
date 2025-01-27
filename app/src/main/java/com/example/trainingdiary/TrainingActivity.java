package com.example.trainingdiary;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListAdapter;
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
import com.example.trainingdiary.Adapters.SetListAdapter;
import com.example.trainingdiary.Models.Exercise;
import com.example.trainingdiary.Models.Set;
import com.example.trainingdiary.Models.Training;
import com.example.trainingdiary.Models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TrainingActivity extends AppCompatActivity {

    static ArrayList<Exercise> _exercises = new ArrayList<>();
    static LocalDate _date;
    static LocalTime _time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_training);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://trainingdiary-b09f5-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference table = database.getReference("User");

        Button startExerciseButton = findViewById(R.id.startExerciseButton);
        Button finishTrainingButton = findViewById(R.id.finishTrainingButton);
        Button cancelTrainingButton = findViewById(R.id.cancelTrainingButton);
        ListView listOfExercises = findViewById(R.id.listOfExercises);

        Bundle arguments = getIntent().getExtras();
        if(arguments != null && arguments.containsKey("name")) {
            String name = arguments.getString("name");
            int position = arguments.getInt("position");
            ArrayList<Set> sets = new ArrayList<>(ExerciseActivity._sets);
            ExerciseActivity._sets.clear();
            _exercises.add(position, new Exercise(name, sets));
            ExerciseListAdapter adapter = new ExerciseListAdapter(this, R.layout.activity_exercise_in_list, _exercises);
            listOfExercises.setAdapter(adapter);
        }
        if (arguments != null && arguments.containsKey("Date")) {
            _date = (LocalDate) arguments.getSerializable("Date");
            _time = (LocalTime) arguments.getSerializable("Time");
        }

        startExerciseButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ExerciseActivity.class);
            startActivity(intent);
        });

        finishTrainingButton.setOnClickListener(v -> {
            String name = ActiveUserInfo.getDefaults("Name", this);
            ArrayList<Exercise> exercises = new ArrayList<>(_exercises);
            String date = _date.getYear() + " " + _date.getMonthValue() + " " + _date.getDayOfMonth();
            String time = _time.getHour() + " " + _time.getMinute();
            Training training = new Training(date, time, exercises);

            table.addValueEventListener(new ValueEventListener() {
                int count = 0;
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(count == 0) {
                        ArrayList<Training> trainings = (ArrayList<Training>) snapshot.child(name).child("trainings").getValue();
                        if (trainings == null) {
                            trainings = new ArrayList<>();
                        }
                        trainings.add(training);
                        table.child(name).child("trainings").setValue(trainings);
                        ++count;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(TrainingActivity.this, "Возникла проблема, скорее всего нет соединения с интернетом", Toast.LENGTH_LONG).show();
                }
            });

            _exercises.clear();
            _date = null;
            _time = null;

            Intent intent = new Intent(this, MainMenuActivity.class);
            startActivity(intent);
        });

        cancelTrainingButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainMenuActivity.class);
            startActivity(intent);
        });

        listOfExercises.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, ExerciseActivity.class);
            intent.putExtra("position", position);
            startActivity(intent);
        });
    }
}