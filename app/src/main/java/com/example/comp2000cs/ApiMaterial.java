package com.example.comp2000cs;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.content.Context;

public class ApiMaterial {

    private static final String BASE_URL = "http://10.224.41.11/comp2000";

    // Fetch an employee by ID
    public static void getEmployeeById(Context context, int id, VolleyResponseListener listener) {
        String url = BASE_URL + "/employees/get/" + id;

        // Make a GET request
        StringRequest request = new StringRequest(Request.Method.GET, url,
                listener::onResponse, // Success callback
                error -> listener.onError(error.toString()) // Error callback
        );

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    public interface VolleyResponseListener {
        void onResponse(String response);
        void onError(String error);
    }
}
