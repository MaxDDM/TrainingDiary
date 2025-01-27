package com.example.trainingdiary;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        String name = ActiveUserInfo.getDefaults("Name", this);
        if(name != null && !name.isEmpty()) {
            Intent intent = new Intent(this, MainMenuActivity.class);
            startActivity(intent);
        }

        Button registerButton = findViewById(R.id.registrationButton);
        Button authorizeButton = findViewById(R.id.authorizationButton);

        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });

        authorizeButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AuthorizeActivity.class);
            startActivity(intent);
        });
    }
}