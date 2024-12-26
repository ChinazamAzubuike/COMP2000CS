package com.example.comp2000cs;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ThirdActivity extends AppCompatActivity {

    private DatabaseMaterial databaseMaterial;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        databaseMaterial = new DatabaseMaterial(this);

        String email = getIntent().getStringExtra("email");
        EmployeeA employee = getEmployeeByEmail(email);


        Button empDetailsButton = findViewById(R.id.empDetails);
        empDetailsButton.setOnClickListener(view -> {
            Intent intent = new Intent(ThirdActivity.this, FourthActivity.class);
            intent.putExtra("email", email); // Replace loggedInEmail with the actual email string
            startActivity(intent);
        });



        String loggedInEmail = getIntent().getStringExtra("email"); // Retrieve email from Intent
        empDetailsButton.setOnClickListener(view -> {
            Intent intent = new Intent(ThirdActivity.this, FourthActivity.class);
            intent.putExtra("email", loggedInEmail); // Pass the email to FourthActivity
            startActivity(intent);
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
