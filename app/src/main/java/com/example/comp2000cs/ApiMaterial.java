package com.example.comp2000cs;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.content.Context;
public class ApiMaterial {

    private static final String BASE_URL = "http://10.224.41.11/comp2000";

    // Get all employees
    public static void getAllEmployees(Context context, VolleyResponseListener listener) {
        String url = BASE_URL + "/employees";

        // Make a GET request
        StringRequest request = new StringRequest(Request.Method.GET, url,
                listener::onResponse, // Success
                error -> listener.onError(error.toString()) // Error
        );

        // Add request to the queue
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    // Define an interface for callbacks
    public interface VolleyResponseListener {
        void onResponse(String response);
        void onError(String error);
    }



}
