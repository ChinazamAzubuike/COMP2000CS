package com.example.comp2000cs;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEmployee extends AppCompatActivity {

    private DatabaseMaterial databaseMaterial;

    private EditText editTextEmployeeId, editTextFirstName, editTextLastName, editTextDepartment, editTextJoiningDate, editTextLeaves, editTextSalary;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        databaseMaterial = new DatabaseMaterial(this);

        // Initialize Views
        editTextEmployeeId = findViewById(R.id.editTextEmployeeId);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextDepartment = findViewById(R.id.editTextDepartment);
        editTextJoiningDate = findViewById(R.id.editTextJoiningDate);
        editTextLeaves = findViewById(R.id.editTextLeaves);
        editTextSalary = findViewById(R.id.editTextSalary);
        Button btnAddEmployee = findViewById(R.id.btnAddEmployee);

        // Add Employee Button
        btnAddEmployee.setOnClickListener(v -> {
            try {
                // Get input values
                int id = Integer.parseInt(editTextEmployeeId.getText().toString());
                String firstName = editTextFirstName.getText().toString();
                String lastName = editTextLastName.getText().toString();
                String department = editTextDepartment.getText().toString();
                String joiningDate = editTextJoiningDate.getText().toString();
                int leaves = Integer.parseInt(editTextLeaves.getText().toString());
                double salary = Double.parseDouble(editTextSalary.getText().toString());

                // Validate if the ID is unique
                if (databaseMaterial.getEmployeeById(id) != null) {
                    Toast.makeText(this, "Employee ID already exists!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create new EmployeeA object
                EmployeeA newEmployee = new EmployeeA(id, firstName, lastName, "", department, joiningDate, leaves, salary);

                // Add employee to the database
                databaseMaterial.addEmployee(newEmployee);

                Toast.makeText(this, "Employee added successfully", Toast.LENGTH_SHORT).show();
                finish(); // Close the activity
            } catch (Exception e) {
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
