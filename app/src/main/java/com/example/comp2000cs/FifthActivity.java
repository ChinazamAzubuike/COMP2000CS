package com.example.comp2000cs;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class FifthActivity extends AppCompatActivity {
    private DatabaseMaterial dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth);

        // Initialize database helper
        dbHelper = new DatabaseMaterial(this);

        // Retrieve email from intent
        String email = getIntent().getStringExtra("email");
        if (email == null || email.isEmpty()) {
            Toast.makeText(this, "Error: No email provided.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Initialize views
        EditText editFirstName = findViewById(R.id.editFirstName);
        EditText editLastName = findViewById(R.id.editLastName);
        Button submitChanges = findViewById(R.id.submitChanges);

        submitChanges.setOnClickListener(view -> {
            String newFirstName = editFirstName.getText().toString().trim();
            String newLastName = editLastName.getText().toString().trim();

            if (newFirstName.isEmpty() || newLastName.isEmpty()) {
                Toast.makeText(this, "Please fill out both fields.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Add the name change request to the database
            if (addNameChangeRequest(email, newFirstName, newLastName)) {
                notifyAdmin(email, newFirstName, newLastName); // Notify the admin
                Toast.makeText(this, "Request submitted. Awaiting admin approval.", Toast.LENGTH_SHORT).show();
                finish(); // Close activity
            } else {
                Toast.makeText(this, "Error submitting request. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean addNameChangeRequest(String email, String newFirstName, String newLastName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("newFirstName", newFirstName);
        values.put("newLastName", newLastName);
        values.put("status", "pending");

        long result = db.insert("name_change_requests", null, values);
        db.close();

        return result != -1;
    }

    private void notifyAdmin(String email, String newFirstName, String newLastName) {
        // Notification Manager
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Create a notification channel for Android 8.0 and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "admin_channel",
                    "Admin Notifications",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Notifications for admin about employee requests");
            notificationManager.createNotificationChannel(channel);
        }

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "admin_channel")
                .setSmallIcon(R.drawable.notifsbell) // Replace with your notification icon
                .setContentTitle("Name Change Request")
                .setContentText("Employee " + email + " requested a name change to: " + newFirstName + " " + newLastName)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        // Send the notification
        notificationManager.notify(1, builder.build());
    }
}
