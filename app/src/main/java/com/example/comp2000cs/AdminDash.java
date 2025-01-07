package com.example.comp2000cs;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.bottomnavigation.BottomNavigationView;
//import com.google.firebase.messaging.FirebaseMessaging;


public class AdminDash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_dash);



        Button viewNameRequestsButton = findViewById(R.id.view_name_requests);

        //navigate to AdminNameRequests
        viewNameRequestsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDash.this, AdminNameRequest.class);
                startActivity(intent);
            }
        });



        Button holidayRequestsButton = findViewById(R.id.holiday_requests);

        // Set an OnClickListener to navigate to LeaveRequests
        holidayRequestsButton.setOnClickListener(view -> {
            Intent intent = new Intent(AdminDash.this, LeaveRequests.class);
            startActivity(intent);
        });




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        Button personalDetailsButton = findViewById(R.id.yourEmployees);
        personalDetailsButton.setOnClickListener(view -> {
            Intent intent = new Intent(AdminDash.this, AdminEmployees.class);
            startActivity(intent);
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(menuItem -> {
            int itemId = menuItem.getItemId();

            // Use if-else instead of switch
            if (itemId == R.id.settings) {
                // Handle settings click
                // Add your action here (e.g., open a settings activity)
                return true;
            } else if (itemId == R.id.home) {
                // Handle home click
                // Add your action here
                return true;
            } else if (itemId == R.id.notifications) {
                // Handle notifications click
                // Add your action here
                return true;
            } else {
                return false;
            }
        });





//        FirebaseMessaging.getInstance().subscribeToTopic("admin")
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        Log.d("Firebase", "Admin subscribed to holiday notifications.");
//                    } else {
//                        Log.e("Firebase", "Failed to subscribe admin.");
//                    }
//                });



    }













}