package com.example.comp2000cs;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditEmployee extends AppCompatActivity {

    private DatabaseMaterial databaseMaterial;
    private EmployeeA employee;

    private EditText editTextFirstName, editTextLastName, editTextEmail, editTextDepartment, editTextJoiningDate, editTextLeaves, editTextSalary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employee);

        databaseMaterial = new DatabaseMaterial(this);

        // Initialize Views
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextDepartment = findViewById(R.id.editTextDepartment);
        editTextJoiningDate = findViewById(R.id.editTextJoiningDate);
        editTextLeaves = findViewById(R.id.editTextLeaves);
        editTextSalary = findViewById(R.id.editTextSalary);
        Button btnSave = findViewById(R.id.btnSave);

        // Get the employee ID passed from the adapter
        int employeeId = getIntent().getIntExtra("employeeId", -1);

        // Fetch employee details from the database
        employee = databaseMaterial.getEmployeeById(employeeId);

        if (employee != null) {
            // Populate the fields with existing data
            editTextFirstName.setText(employee.getFirstname());
            editTextLastName.setText(employee.getLastname());
            editTextEmail.setText(employee.getEmail());
            editTextDepartment.setText(employee.getDepartment());
            editTextJoiningDate.setText(employee.getJoiningdate());
            editTextLeaves.setText(String.valueOf(employee.getLeaves()));
            editTextSalary.setText(String.valueOf(employee.getSalary()));
        }

        // Save button logic
        btnSave.setOnClickListener(v -> {
            // Update employee details
            employee.setFirstname(editTextFirstName.getText().toString());
            employee.setLastname(editTextLastName.getText().toString());
            employee.setEmail(editTextEmail.getText().toString());
            employee.setDepartment(editTextDepartment.getText().toString());
            employee.setJoiningdate(editTextJoiningDate.getText().toString());
            employee.setLeaves(Integer.parseInt(editTextLeaves.getText().toString()));
            employee.setSalary(Double.parseDouble(editTextSalary.getText().toString()));

            // Save the updated employee in the database
            databaseMaterial.updateEmployee(employee);

            Toast.makeText(this, "Employee updated successfully", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity
        });

    }
}
