package com.example.comp2000cs;

import android.os.Bundle;

//import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.view.View;
import android.widget.Button;


public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Find the button by its ID
        Button loginButton = findViewById(R.id.login_button);

        // Set an OnClickListener for the login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an intent to start ThirdActivity
                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                startActivity(intent); // Start the third activity
            }
        });
    }
}