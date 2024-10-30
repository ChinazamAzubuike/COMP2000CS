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

//EMPLOYEE DASHBOARD

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        // Find the button by its ID
        Button empDetailsButton = findViewById(R.id.empDetails);

        // From employee dashboard to viewing profile
        empDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an intent to start FourthActivity
                Intent intent = new Intent(ThirdActivity.this, FourthActivity.class);
                startActivity(intent); // Start the fourth activity
            }
        });


//From Employee dashboard to View booked holidays
        Button empHolidaysButton = findViewById(R.id.empHolidays);
        empHolidaysButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an intent to start SixthActivity
                Intent intent = new Intent(ThirdActivity.this, SixthActivity.class);
                startActivity(intent);
            }
        });

    }
}