package com.example.comp2000cs;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import java.util.ArrayList;
import java.util.List;
import android.util.Log;
import android.widget.Button;
//import android.view.View;


//import org.json.JSONArray;
import org.json.JSONObject;


public class AdminEmployees extends AppCompatActivity
{



    private EmployeeAdapter adapter;
    private List<EmployeeA> employeeList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_employees);


        try (DatabaseMaterial databaseMaterial = new DatabaseMaterial(this))
        {


            RecyclerView recyclerView = findViewById(R.id.recyclerViewEmployees);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            Button btnAddEmployee = findViewById(R.id.btnAddEmployee);
            btnAddEmployee.setOnClickListener(v -> {
                // Launch AddEmployeeActivity
                Intent intent = new Intent(AdminEmployees.this, AddEmployee.class);
                startActivity(intent);
            });

            employeeList = databaseMaterial.getAllEmployees();
            adapter = new EmployeeAdapter(this, employeeList);
            recyclerView.setAdapter(adapter);

            loadEmployees();

        } catch (Exception e) {
            Log.e("DB_ERROR", "Error initializing or using the database", e);
        }



    }


    private void loadEmployees() {
        int[] employeeIds = {1648, 1649, 1650, 1651, 2953}; // Your specified employee IDs
        DatabaseMaterial databaseMaterial = new DatabaseMaterial(this);

        for (int id : employeeIds) {
            ApiMaterial.getEmployeeById(this, id, new ApiMaterial.VolleyResponseListener() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject obj = new JSONObject(response);

                        // Check if the employee already exists in the database
                        EmployeeA existingEmployee = databaseMaterial.getEmployeeById(obj.getInt("id"));
                        if (existingEmployee == null) {
                            // Create and add the employee if it doesn't already exist
                            EmployeeA employee = new EmployeeA(
                                    obj.getInt("id"),
                                    obj.optString("firstname", "N/A"),
                                    obj.optString("lastname", "N/A"),
                                    obj.optString("email", "N/A"),
                                    obj.optString("department", "N/A"),
                                    obj.optString("joiningdate", "N/A"),
                                    obj.optInt("leaves", 0),
                                    obj.optDouble("salary", 0.0)
                            );

                            databaseMaterial.addEmployee(employee); // Save to SQLite
                            employeeList.add(employee);             // Add to local list
                            adapter.notifyItemInserted(employeeList.size() - 1); // Update RecyclerView
                        }
                    } catch (Exception e) {
                        Log.e("JSON_PARSE_ERROR", "Error parsing JSON", e);
                    }
                }

                @Override
                public void onError(String error) {
                    Log.e("API_ERROR", "Error fetching employee with ID: " + id + ", " + error);
                }
            });
        }
    }



}


