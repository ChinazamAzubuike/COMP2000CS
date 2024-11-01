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

public class SixthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sixth);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // BACK BUTTON from employee profile TO DASHBOARD
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> finish());



        ImageView notificationsIcon = findViewById(R.id.imageView3);


        notificationsIcon.setOnClickListener(view -> {
            Intent intent = new Intent(SixthActivity.this, EighthActivity.class);
            startActivity(intent);
        });

        ImageView profileIcon = findViewById(R.id.imageView);

// PROFILE ICON BUTTON
        profileIcon.setOnClickListener(view -> {
            Intent intent = new Intent(SixthActivity.this, FourthActivity.class);
            startActivity(intent);
        });

    }
}