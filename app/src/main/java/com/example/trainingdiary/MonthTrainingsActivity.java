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

import com.example.trainingdiary.Adapters.ExerciseListAdapter;
import com.example.trainingdiary.Adapters.MonthsListAdapter;
import com.example.trainingdiary.Adapters.TrainingListAdapter;
import com.example.trainingdiary.Models.Training;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class MonthTrainingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_month_trainings);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://trainingdiary-b09f5-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference table = database.getReference("User");

        Button returnToArchiveButton = findViewById(R.id.returnToArchiveButton);
        ListView listOfTrainings = findViewById(R.id.trainingsListView);

        Bundle arguments = getIntent().getExtras();
        String date = arguments.getString("date");

        table.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = ActiveUserInfo.getDefaults("Name", MonthTrainingsActivity.this);
                GenericTypeIndicator<ArrayList<Training>> genericTypeIndicator = new GenericTypeIndicator<ArrayList<Training>>() {};
                ArrayList<Training> trainings = snapshot.child(name).child("trainings").getValue(genericTypeIndicator);
                ArrayList<String> dates = new ArrayList<>(Arrays.asList(date.split(" ")));
                String month = dates.get(1);
                String year = dates.get(0);
                ArrayList<Training> monthTrainings = new ArrayList<>();
                for(int i = 0; i < trainings.size(); ++i) {
                    ArrayList<String> dates1 = new ArrayList<>(Arrays.asList(trainings.get(i).date.split(" ")));
                    if(Objects.equals(dates1.get(0), year) && Objects.equals(dates1.get(1), month)) {
                        monthTrainings.add(trainings.get(i));
                    }
                }
                TrainingListAdapter adapter = new TrainingListAdapter(MonthTrainingsActivity.this, R.layout.activity_training_in_list, monthTrainings);
                listOfTrainings.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MonthTrainingsActivity.this, "Возникла проблема, скорее всего нет соединения с интернетом", Toast.LENGTH_LONG).show();
            }
        });

        returnToArchiveButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ArchiveActivity.class);
            startActivity(intent);
        });

        listOfTrainings.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, TrainingDisplayActivity.class);
            intent.putExtra("position", position);
            intent.putExtra("date", date);
            startActivity(intent);
        });
    }
}
