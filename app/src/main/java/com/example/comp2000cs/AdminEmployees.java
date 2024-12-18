package com.example.comp2000cs;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.recyclerview.widget.LinearLayoutManager;
//
//import java.util.ArrayList;
//import java.util.List;
import android.util.Log;

import org.json.JSONArray;
//import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AdminEmployees extends AppCompatActivity {

    private TextView employeeDataTextView;
    private RecyclerView recyclerView;
    private EmployeeAdapter adapter;
    private List<EmployeeA> employeeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_employees);

        // Link TextView
      //  employeeDataTextView = findViewById(R.id.employee_data);

        // Fetch employee data
        ApiMaterial.getAllEmployees(this, new ApiMaterial.VolleyResponseListener() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        employeeList.add(new EmployeeA(
                                obj.getInt("id"),
                                obj.getString("firstname"),
                                obj.getString("lastname"),
                                obj.getString("email"),
                                obj.getString("department"),
                                obj.getString("joiningdate"),
                                obj.optInt("leaves"),
                                obj.optDouble("salary")
                        ));
                    }
                    adapter = new EmployeeAdapter(employeeList);
                    recyclerView.setAdapter(adapter);
                } catch (Exception e) {
                    Log.e("AdminEmployees", "Error parsing JSON", e);
                }
            }

            @Override
            public void onError(String error) {
                // Handle error
            }
        });
    }
}