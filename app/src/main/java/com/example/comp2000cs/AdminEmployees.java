package com.example.comp2000cs;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import java.util.ArrayList;
import java.util.List;
import android.util.Log;
//import android.view.View;


//import org.json.JSONArray;
import org.json.JSONObject;


public class AdminEmployees extends AppCompatActivity
{

    private EmployeeAdapter adapter;
    private List<EmployeeA> employeeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_employees);

        // Initialize the database
        DatabaseMaterial databaseMaterial = new DatabaseMaterial(this);

        // Set up RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerViewEmployees);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch employees from the database
        employeeList = databaseMaterial.getAllEmployees();
        adapter = new EmployeeAdapter(employeeList);
        recyclerView.setAdapter(adapter);

        // Load employees from the API
        loadEmployees();
    }


    private void loadEmployees() {
        int[] employeeIds = {1440, 1441, 1403, 1407}; // Your employee IDs

        try (DatabaseMaterial databaseMaterial = new DatabaseMaterial(this)) { // Try-with-resources
            for (int id : employeeIds) {
                ApiMaterial.getEmployeeById(this, id, new ApiMaterial.VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);

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

                            databaseMaterial.addEmployee(employee);

                            employeeList.add(employee);
                            adapter.notifyItemInserted(employeeList.size() - 1);

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
        } catch (Exception e) {
            Log.e("DB_ERROR", "Error initializing or using the database", e);
        }
    }


}


