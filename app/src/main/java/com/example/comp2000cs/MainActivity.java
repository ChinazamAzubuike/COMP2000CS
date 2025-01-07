package com.example.comp2000cs;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Replace activity_main with the desired layout

        // Enable Edge-to-Edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Setup login button
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class); // Navigate to employee login
            startActivity(intent);
        });



        // Setup employee management button
//        Button btnEmployee = findViewById(R.id.btn_employee);
//        btnEmployee.setOnClickListener(v -> {
//            Intent intent = new Intent(MainActivity.this, AdminEmployees.class); // Navigate to employee management
//            startActivity(intent);
//        });

        // Request notification permission for Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1001);
            }
        }

        // Schedule the salary increment periodic task
        scheduleSalaryIncrementTask();
    }

    // Method to schedule salary increment task
    private void scheduleSalaryIncrementTask() {
        PeriodicWorkRequest salaryIncrementWorkRequest = new PeriodicWorkRequest.Builder(
                SalaryIncrementWorker.class,
                1, TimeUnit.DAYS // Repeat every day
        ).build();

        WorkManager.getInstance(this).enqueue(salaryIncrementWorkRequest);
    }
}
