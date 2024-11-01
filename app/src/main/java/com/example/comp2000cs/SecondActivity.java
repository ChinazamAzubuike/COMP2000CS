package com.example.comp2000cs;

import android.os.Bundle;

//import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
//import android.view.View;
import android.widget.Button;


public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(view -> {
            Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
            startActivity(intent); // Start the third activity
        });
    }
}