package com.example.comp2000cs;

import android.content.Intent;
import android.os.Bundle;
//import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    private EditText emailInput, passwordInput;
    private DatabaseMaterial dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Initialize database helper
        dbHelper = new DatabaseMaterial(this);

        // Initialize views
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        Button login_button = findViewById(R.id.login_button);

//        // Initialize database helper
//        dbHelper = new DatabaseMaterial(this);

        login_button.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(SecondActivity.this, "Please enter all fields.", Toast.LENGTH_SHORT).show();
            } else {
                handleLogin(email, password);
            }
        });
    }

    private void handleLogin(String email, String password) {
        if (dbHelper.validateLogin(email, password)) {
            boolean isAdmin = dbHelper.isAdmin(email);

            Intent intent;
            if (isAdmin) {
                intent = new Intent(SecondActivity.this, AdminDash.class); // Admin Dashboard
            } else {
                intent = new Intent(SecondActivity.this, ThirdActivity.class); // Employee Dashboard
            }

            // Optionally pass the email to the next activity
            intent.putExtra("email", email);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Invalid credentials. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }
}
