package com.example.comp2000cs;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NinthActivity extends AppCompatActivity {

    private DatabaseMaterial dbHelper;
    private EmployeeNotificationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ninth);

        // Initialize dbHelper
        dbHelper = new DatabaseMaterial(this);

        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        String loggedInEmail = sharedPreferences.getString("loggedInEmail", null);

        if (loggedInEmail == null || loggedInEmail.isEmpty()) {
            Toast.makeText(this, "Error: No logged-in user found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        RecyclerView recyclerView = findViewById(R.id.notificationsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TextView noNotificationsTextView = findViewById(R.id.noNotificationsTextView);

        List<EmployeeNotifications> notifications = dbHelper.getAllNotifications(loggedInEmail);
        if (notifications.isEmpty()) {
            noNotificationsTextView.setVisibility(View.VISIBLE); // Show "No Notifications" message
        } else {
            noNotificationsTextView.setVisibility(View.GONE); // Hide "No Notifications" message
            adapter = new EmployeeNotificationAdapter(notifications);
            recyclerView.setAdapter(adapter);
        }
    }
}
