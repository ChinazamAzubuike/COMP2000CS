package com.example.comp2000cs;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

//import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageView;



public class FourthActivity extends AppCompatActivity {

    //EMPLOYEE PROFILE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fourth);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        //Personal details button - Main Profile to Personal details page
        Button personalDetailsButton = findViewById(R.id.personalDetailsButton);
        personalDetailsButton.setOnClickListener(view -> {
            Intent intent = new Intent(FourthActivity.this, FifthActivity.class);
            startActivity(intent);
        });


// BACK BUTTON
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> finish());


        ImageView notificationsIcon = findViewById(R.id.imageView3);



        //notification button
        notificationsIcon.setOnClickListener(view -> {
            Intent intent = new Intent(FourthActivity.this, EighthActivity.class);
            startActivity(intent);
        });


    }
}