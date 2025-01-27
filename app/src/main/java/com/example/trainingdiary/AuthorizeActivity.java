package com.example.trainingdiary;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.Objects;

public class AuthorizeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_authorize);

        EditText name = findViewById(R.id.userNameEditText);
        EditText password = findViewById(R.id.passwordEditText);
        Button authorizeButton = findViewById(R.id.authorizeButton);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://trainingdiary-b09f5-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference table = database.getReference("User");

        authorizeButton.setOnClickListener(v -> {
            table.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (name.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                        authorizeButton.setText("Заполните все поля и нажмите ещё раз");
                        return;
                    }
                    if(snapshot.child(name.getText().toString()).exists()) {
                        User user = snapshot.child(name.getText().toString()).getValue(User.class);
                        if(password.getText().toString().equals(Objects.requireNonNull(user).getPassword())) {
                            ActiveUserInfo.setDefaults("Name", name.getText().toString(), AuthorizeActivity.this);
                            Intent intent = new Intent(AuthorizeActivity.this, MainMenuActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(AuthorizeActivity.this, "Неверный пароль", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(AuthorizeActivity.this, "Пользователя с таким логином нет", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(AuthorizeActivity.this, "Возникла проблема, скорее всего нет соединения с интернетом", Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}