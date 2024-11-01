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
import android.widget.ImageView;

//EMPLOYEE DASHBOARD

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);


        Button empDetailsButton = findViewById(R.id.empDetails);

        // From employee dashboard to viewing profile

           empDetailsButton.setOnClickListener(view -> {
                Intent intent = new Intent(ThirdActivity.this, FourthActivity.class);
                startActivity(intent);
            });




//From Employee dashboard to View booked holidays
        Button empHolidaysButton = findViewById(R.id.empHolidays);
        empHolidaysButton.setOnClickListener(view -> {
            Intent intent = new Intent(ThirdActivity.this, SixthActivity.class);
            startActivity(intent);
        });


//From Dashboard to request a holiday
        Button empRequestButton = findViewById(R.id.empRequest);
        empRequestButton.setOnClickListener(view -> {
            Intent intent = new Intent(ThirdActivity.this, SeventhActivity.class);
            startActivity(intent);
        });



        ImageView notificationsIcon = findViewById(R.id.imageView3);

// Set an OnClickListener using a lambda to navigate to the EighthActivity
        notificationsIcon.setOnClickListener(view -> {
            Intent intent = new Intent(ThirdActivity.this, NinthActivity.class);
            startActivity(intent);
        });



        ImageView profileIcon = findViewById(R.id.imageView);

// third page to FourthActivity
        profileIcon.setOnClickListener(view -> {
            Intent intent = new Intent(ThirdActivity.this, FourthActivity.class);
            startActivity(intent);
        });


    }
}