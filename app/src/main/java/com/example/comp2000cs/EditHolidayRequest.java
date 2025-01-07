package com.example.comp2000cs;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class EditHolidayRequest extends AppCompatActivity {

    private DatabaseMaterial dbHelper;
    private int requestId;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_holiday_request);

        dbHelper = new DatabaseMaterial(this);

        // Retrieve data passed from SixthActivity
        Intent intent = getIntent();
        requestId = intent.getIntExtra("requestId", -1); // Holiday request ID
        email = intent.getStringExtra("email");

        if (requestId == -1 || email == null || email.isEmpty()) {
            Toast.makeText(this, "Error: Missing request data.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Initialize views
        EditText editStartDateInput = findViewById(R.id.editStartDateInput);
        EditText editEndDateInput = findViewById(R.id.editEndDateInput);
        Button submitEditHoliday = findViewById(R.id.submitEditHoliday);
        Button cancelEditHoliday = findViewById(R.id.cancelEditHoliday);

        // Set click listener for Cancel button
        cancelEditHoliday.setOnClickListener(v -> finish()); // Close activity

        // Set click listener for Submit button
        submitEditHoliday.setOnClickListener(v -> {
            String newStartDate = editStartDateInput.getText().toString().trim();
            String newEndDate = editEndDateInput.getText().toString().trim();

            if (TextUtils.isEmpty(newStartDate) || TextUtils.isEmpty(newEndDate)) {
                Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                // Validate the dates
                LocalDate startDate = LocalDate.parse(newStartDate);
                LocalDate endDate = LocalDate.parse(newEndDate);

                if (endDate.isBefore(startDate)) {
                    Toast.makeText(this, "End date cannot be before start date.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Calculate the total days
                int totalDays = (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;

                // Update the holiday request in the database
                boolean success = dbHelper.editHolidayRequest(requestId, email, newStartDate, newEndDate, totalDays);
                if (success) {
                    Toast.makeText(this, "Holiday request updated. Awaiting admin approval.", Toast.LENGTH_SHORT).show();
                    finish(); // Close the activity
                } else {
                    Toast.makeText(this, "Error updating request. Please try again.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Invalid date format. Please use YYYY-MM-DD.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
