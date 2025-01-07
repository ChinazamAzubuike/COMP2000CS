package com.example.comp2000cs;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FifthActivity extends AppCompatActivity {
    private DatabaseMaterial dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth);

        dbHelper = new DatabaseMaterial(this);

        // Retrieve email from the previous activity
        String email = getIntent().getStringExtra("email");
        if (email == null || email.isEmpty()) {
            Toast.makeText(this, "Error: No email provided.", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity if the email is missing
            return;
        }

        // Initialize views
        EditText firstNameInput = findViewById(R.id.editFirstName);
        EditText lastNameInput = findViewById(R.id.editLastName);
        Button submitButton = findViewById(R.id.submitChanges);

        submitButton.setOnClickListener(view -> {
            String newFirstName = firstNameInput.getText().toString().trim();
            String newLastName = lastNameInput.getText().toString().trim();

            if (TextUtils.isEmpty(newFirstName) || TextUtils.isEmpty(newLastName)) {
                Toast.makeText(FifthActivity.this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Add the name change request to the database
            boolean success = dbHelper.addNameChangeRequest(email, newFirstName, newLastName);
            if (success) {
                Toast.makeText(FifthActivity.this, "Name change request submitted!", Toast.LENGTH_SHORT).show();

                // Notify the admin about the name change request
                notifyAdminForNameChange(email, newFirstName, newLastName);
            } else {
                Toast.makeText(FifthActivity.this, "Error submitting request. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void notifyAdminForNameChange(String email, String newFirstName, String newLastName) {
        // Prepare the notification message
        String title = "New Name Change Request";
        String body = "Employee with email " + email + " requested to change their name to "
                + newFirstName + " " + newLastName + ".";

        // Trigger the local notification
//        MyFirebaseMessagin gService.handleIncomingNotification(
//                this, // Pass the current context
//                title, // Title of the notification
//                body  // Body of the notification
//        );
    }
}
