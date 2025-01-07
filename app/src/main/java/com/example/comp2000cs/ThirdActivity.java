package com.example.comp2000cs;

import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ThirdActivity extends AppCompatActivity {

    private DatabaseMaterial databaseMaterial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        databaseMaterial = new DatabaseMaterial(this);

        // Retrieve email passed from the login screen
        String email = getIntent().getStringExtra("email");


        Button viewBookedHolidaysButton = findViewById(R.id.view_booked_holidays);





        viewBookedHolidaysButton.setOnClickListener(v -> {
            Intent intent = new Intent(ThirdActivity.this, SixthActivity.class);
            intent.putExtra("email", email); // Pass employee email to SixthActivity
            startActivity(intent);
        });



        if (email == null || email.isEmpty()) {
            Toast.makeText(this, "Error: No email provided.", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity if no email is provided
            return;
        }

        // Employee details button
        Button empDetailsButton = findViewById(R.id.empDetails);
        empDetailsButton.setOnClickListener(view -> {
            Intent intent = new Intent(ThirdActivity.this, FourthActivity.class);
            intent.putExtra("email", email); // Pass the email to FourthActivity
            startActivity(intent);
        });

        // Holiday request button
        Button leaveRequestsButton = findViewById(R.id.request_holiday);
        leaveRequestsButton.setOnClickListener(view -> {
            Intent intent = new Intent(ThirdActivity.this, SeventhActivity.class);
            intent.putExtra("email", email); // Pass the email to SeventhActivity
            startActivity(intent);
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);


        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.notifications) {
                //navigate to NinthActivity when bell icon is clicked
                Intent intent = new Intent(ThirdActivity.this, NinthActivity.class);
                startActivity(intent);
                return true;
            }
            return false;
        });


    }

    private EmployeeA getEmployeeByEmail(String email) {
        for (EmployeeA employee : databaseMaterial.getAllEmployees()) {
            if (employee.getEmail().equals(email)) {
                return employee;
            }
        }
        return null;
    }
}
