package com.example.comp2000cs;

import android.content.Intent;
import android.os.Bundle;
//import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SeventhActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_seventh);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> finish());


        ImageView notificationsIcon = findViewById(R.id.imageView3);

// Set an OnClickListener using a lambda to navigate to the EighthActivity
        notificationsIcon.setOnClickListener(view -> {
            Intent intent = new Intent(SeventhActivity.this, EighthActivity.class);
            startActivity(intent);
        });

        notificationsIcon.setOnClickListener(view -> {
            Intent intent = new Intent(SeventhActivity.this, NinthActivity.class);
            startActivity(intent);
        });


        ImageView profileIcon = findViewById(R.id.imageView);

// PROFILE ICON BUTTON
        profileIcon.setOnClickListener(view -> {
            Intent intent = new Intent(SeventhActivity.this, FourthActivity.class);
            startActivity(intent);
        });

    }
}