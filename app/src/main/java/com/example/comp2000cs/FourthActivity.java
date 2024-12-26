package com.example.comp2000cs;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FourthActivity extends AppCompatActivity {
    private DatabaseMaterial dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        // Initialize database helper
        dbHelper = new DatabaseMaterial(this);

        // Get the email from the intent
        String email = getIntent().getStringExtra("email");
        Log.d("FourthActivity", "Email received: " + email);

        if (email == null || email.isEmpty()) {
            Toast.makeText(this, "Error: No email provided.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

// Fetch and display employee details
        EmployeeA employee = dbHelper.getEmployeeByEmail(email);
        if (employee != null) {
            displayEmployeeDetails(employee);
        } else {
            Toast.makeText(this, "Error: Employee not found.", Toast.LENGTH_SHORT).show();
        }


        // Initialize the Edit Details button
        Button editDetailsButton = findViewById(R.id.edit_details);
        editDetailsButton.setOnClickListener(view -> {
            // Create an Intent to navigate to FifthActivity
            Intent intent = new Intent(FourthActivity.this, FifthActivity.class);

            // Pass the email to FifthActivity
            intent.putExtra("email", email);

            // Start FifthActivity
            startActivity(intent);
        });
    }

    private EmployeeA getEmployeeDetails(String email) {
        return dbHelper.getAllEmployees().stream()
                .filter(emp -> emp.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    private void displayEmployeeDetails(EmployeeA employee) {
        // Initialize and set TextViews for employee details
        TextView idView = findViewById(R.id.textViewId);
        TextView nameView = findViewById(R.id.textViewName);
        TextView deptView = findViewById(R.id.textViewDepartment);
        TextView emailView = findViewById(R.id.textViewEmail);
        TextView salaryView = findViewById(R.id.textViewSalary);
        TextView leavesView = findViewById(R.id.textViewLeaves);

        idView.setText(getString(R.string.employee_id, employee.getId()));
        nameView.setText(getString(R.string.employee_name, employee.getFirstname(), employee.getLastname()));
        deptView.setText(getString(R.string.employee_department, employee.getDepartment()));
        emailView.setText(getString(R.string.employee_email, employee.getEmail()));
        salaryView.setText(getString(R.string.employee_salary, employee.getSalary()));
        leavesView.setText(getString(R.string.employee_leaves, employee.getLeaves()));
    }
}
