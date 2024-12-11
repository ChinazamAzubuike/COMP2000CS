package com.example.comp2000cs;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;

import org.json.JSONArray;
//import org.json.JSONException;
import org.json.JSONObject;

public class AdminEmployees extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_employees);

        // Initialize RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // API URL
        String url = "http://10.224.41.11/comp2000/employees";

        // Volley RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);

        // Make API request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        // Parse JSON response
                        JSONArray jsonArray = new JSONArray(response);
                        List<EmployeeA> employees = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject employeeJson = jsonArray.getJSONObject(i);

                            EmployeeA employee = new EmployeeA(
                                    employeeJson.getInt("id"),
                                    employeeJson.getString("firstname"),
                                    employeeJson.getString("lastname"),
                                    employeeJson.getString("email"),
                                    employeeJson.getString("department"),
                                    employeeJson.getDouble("salary"),
                                    employeeJson.getString("joiningdate"),
                                    employeeJson.getInt("leaves")
                            );
                            employees.add(employee);
                        }

                        // Set RecyclerView Adapter
                        EmployeeAdapter adapter = new EmployeeAdapter(employees);
                        recyclerView.setAdapter(adapter);

                    }
                    catch (Exception e) {
                        Log.e("AdminEmployees", "Error parsing JSON", e);
                    }
                },
                error -> Log.e("AdminEmployees", "Error fetching data", error)
        );



        queue.add(stringRequest);
    }
}