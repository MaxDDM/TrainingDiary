package com.example.trainingdiary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trainingdiary.Models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        EditText name = findViewById(R.id.userNameEditText);
        EditText password = findViewById(R.id.passwordEditText);
        Button registerButton = findViewById(R.id.registerButton);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://trainingdiary-b09f5-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference table = database.getReference("User");

        registerButton.setOnClickListener(v -> {
            table.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (name.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                        registerButton.setText("Заполните все поля и нажмите ещё раз");
                        return;
                    }
                    if(snapshot.child(name.getText().toString()).exists()) {
                        if (!name.getText().toString().isEmpty()) {
                            Toast.makeText(RegisterActivity.this, "Это имя занято", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        ActiveUserInfo.setDefaults("Name", name.getText().toString(), RegisterActivity.this);
                        User user = new User(password.getText().toString());
                        table.child(name.getText().toString()).setValue(user);
                        name.setText("");
                        password.setText("");
                        Toast.makeText(RegisterActivity.this, "Успешная регистрация", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegisterActivity.this, MainMenuActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(RegisterActivity.this, "Возникла проблема, скорее всего нет соединения с интернетом", Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}