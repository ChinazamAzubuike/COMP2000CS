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

        try (DatabaseMaterial databaseMaterial = new DatabaseMaterial(this)) {


            databaseMaterial.clearEmployees();

            RecyclerView recyclerView = findViewById(R.id.recyclerViewEmployees);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            employeeList = databaseMaterial.getAllEmployees();
            adapter = new EmployeeAdapter(this, employeeList);
            recyclerView.setAdapter(adapter);

            loadEmployees();

        } catch (Exception e) {
            Log.e("DB_ERROR", "Error initializing or using the database", e);
        }



    }


    private void loadEmployees() {
        int[] employeeIds = {1561, 1562, 1564, 1564, 1565}; // Your specified employee IDs
        DatabaseMaterial databaseMaterial = new DatabaseMaterial(this);

        // Loop through the IDs and fetch each employee
        for (int id : employeeIds) {
            ApiMaterial.getEmployeeById(this, id, new ApiMaterial.VolleyResponseListener() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject obj = new JSONObject(response);
                        int position = employeeList.size(); // Current position before adding

                        // Check if the employee already exists in the database
                        boolean exists = false;
                        for (EmployeeA e : employeeList) {
                            if (e.getId() == obj.getInt("id")) {
                                exists = true;
                                break;
                            }
                        }

                        if (!exists) {
                            // Create and add the employee
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
                            adapter.notifyItemInserted(position);   // Update RecyclerView
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


