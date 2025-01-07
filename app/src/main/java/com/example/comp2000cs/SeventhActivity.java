package com.example.comp2000cs;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class SeventhActivity extends AppCompatActivity {
    private DatabaseMaterial dbHelper;
    private int remainingLeaveDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seventh);

        dbHelper = new DatabaseMaterial(this);

        // Retrieve email passed from the previous activity
        String email = getIntent().getStringExtra("email");
        if (email == null || email.isEmpty()) {
            Toast.makeText(this, "Error: No email provided.", Toast.LENGTH_SHORT).show();
            finish(); // Close activity if email is missing
            return;
        }

        // Initialize views
        TextView remainingDaysText = findViewById(R.id.remainingLeaveDays);
        EditText startDateInput = findViewById(R.id.startDateInput);
        EditText endDateInput = findViewById(R.id.endDateInput);
        Button bookHolidayButton = findViewById(R.id.bookHolidayButton);

        // Fetch remaining leave days from the database
        remainingLeaveDays = dbHelper.getRemainingLeaveDays(email);
        remainingDaysText.setText("Remaining leave days: " + remainingLeaveDays);

        bookHolidayButton.setOnClickListener(view -> {
            String startDate = startDateInput.getText().toString().trim();
            String endDate = endDateInput.getText().toString().trim();

            if (TextUtils.isEmpty(startDate) || TextUtils.isEmpty(endDate)) {
                Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                // Parse and validate the dates
                LocalDate start = LocalDate.parse(startDate);
                LocalDate end = LocalDate.parse(endDate);

                if (end.isBefore(start)) {
                    Toast.makeText(this, "End date cannot be before start date.", Toast.LENGTH_SHORT).show();
                    return;
                }

                long totalDays = ChronoUnit.DAYS.between(start, end) + 1;

                if (totalDays > remainingLeaveDays) {
                    Toast.makeText(this, "You cannot book more days than your remaining leave.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Add holiday request to the database
                boolean success = dbHelper.addHolidayRequest(email, startDate, endDate, (int) totalDays);
                if (success) {
                    remainingLeaveDays -= totalDays;
                    remainingDaysText.setText("Remaining leave days: " + remainingLeaveDays);
                    Toast.makeText(this, "Holiday request submitted!", Toast.LENGTH_SHORT).show();

                    // Notify admin
                    triggerLocalNotification("Admin", "New holiday request from " + email);
                } else {
                    Toast.makeText(this, "Error submitting request. Please try again.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Invalid date format. Please use YYYY-MM-DD.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Method to trigger a local notification.
     */
    private void triggerLocalNotification(String title, String message) {
        // Create a unique notification channel (required for Android 8.0+)
        String channelId = "holiday_notifications";
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Holiday Notifications",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Notifications for holiday requests");
            notificationManager.createNotificationChannel(channel);
        }

        // Build the notification
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.notifsbell2)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true); // Dismiss the notification when clicked

        // Send the notification
        int notificationId = (int) System.currentTimeMillis(); // Use a unique ID for each notification
        notificationManager.notify(notificationId, notificationBuilder.build());
    }
}
