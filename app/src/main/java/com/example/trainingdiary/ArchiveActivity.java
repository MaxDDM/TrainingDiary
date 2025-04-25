package com.example.trainingdiary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.trainingdiary.Adapters.MonthsListAdapter;
import com.example.trainingdiary.Models.Training;
import com.example.trainingdiary.Models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ArchiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_archive);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://trainingdiary-b09f5-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference table = database.getReference("User");

        Button backToMainButton = findViewById(R.id.backToMainButton);
        ListView listOfMonths = findViewById(R.id.monthsListView);

        table.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = ActiveUserInfo.getDefaults("Name", ArchiveActivity.this);
                GenericTypeIndicator<ArrayList<Training>> genericTypeIndicator = new GenericTypeIndicator<ArrayList<Training>>() {};
                ArrayList<Training> trainings = snapshot.child(name).child("trainings").getValue(genericTypeIndicator);
                if (trainings == null) {
                    trainings = new ArrayList<>();
                }
                MonthsListAdapter adapter = getMonthsListAdapter(trainings);
                listOfMonths.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ArchiveActivity.this, "Возникла проблема, скорее всего нет соединения с интернетом", Toast.LENGTH_LONG).show();
            }
        });

        backToMainButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainMenuActivity.class);
            startActivity(intent);
        });

        listOfMonths.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, MonthTrainingsActivity.class);
            TextView monthText = view.findViewById(R.id.monthTextView);
            intent.putExtra("date", monthText.getText().toString());
            startActivity(intent);
        });
    }

    @NonNull
    private MonthsListAdapter getMonthsListAdapter(ArrayList<Training> trainings) {
        ArrayList<String> months = new ArrayList<>();
        for(int i = 0; i < trainings.size(); ++i) {
            if(!months.contains(trainings.get(i).date.substring(0, trainings.get(i).date.lastIndexOf(' ')))) {
                months.add(trainings.get(i).date.substring(0, trainings.get(i).date.lastIndexOf(' ')));
            }
        }
        return new MonthsListAdapter(this, R.layout.activity_month_in_list, months);
    }
}