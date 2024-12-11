//package com.example.comp2000cs;
//
//import android.os.Bundle;
//import android.widget.TextView;
//import androidx.appcompat.app.AppCompatActivity;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
////import com.android.volley.Response;
////import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.json.JSONArray;
////import org.json.JSONException;
//import org.json.JSONObject;
//
//public class EmployeeActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_employee);
//
//        TextView textView = findViewById(R.id.textView);
//        textView.setText(getString(R.string.loading)); // Set loading text
//
//        String url = "http://10.224.41.11/comp2000/employees";
//        RequestQueue queue = Volley.newRequestQueue(this);
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                response -> {
//                    try {
//                        // Parse JSON response
//                        JSONArray jsonArray = new JSONArray(response);
//                        List<EmployeeA> employees = new ArrayList<>();
//
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            JSONObject employeeJson = jsonArray.getJSONObject(i);
//
//                            // Create an EmployeeA object for each JSON object
//                            EmployeeA employee = new EmployeeA(
//                                    employeeJson.getInt("id"),
//                                    employeeJson.getString("firstname"),
//                                    employeeJson.getString("lastname"),
//                                    employeeJson.getString("email"),
//                                    employeeJson.getString("department"),
//                                    employeeJson.getDouble("salary"),
//                                    employeeJson.getString("joiningdate"),
//                                    employeeJson.getInt("leaves")
//                            );
//                            employees.add(employee);
//                        }
//
//                        // Show the number of employees in the TextView
//                        textView.setText(getString(R.string.total_employees, employees.size()));
//
//                    } catch (Exception e) {
//                        textView.setText(getString(R.string.error_parsing, e.getMessage()));
//                    }
//                },
//                error -> textView.setText(getString(R.string.error_request, error.toString()))
//        );
//
//        queue.add(stringRequest);
//    }
//
//
//
//}