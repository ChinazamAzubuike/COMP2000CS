package com.example.comp2000cs;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdminNameRequest extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NameChangeRequestAdapter adapter;
    private DatabaseMaterial dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_name_request);

        // Initialize database helper
        dbHelper = new DatabaseMaterial(this);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.requestsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load pending requests
        loadPendingRequests();

        Log.d("AdminNameRequest", "Activity started!");
        Log.d("AdminNameRequest", "Activity opened from notification");

        Intent intent = new Intent(this, AdminNameRequest.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

    }



    private void loadPendingRequests() {
        // Fetch pending name change requests from the database
        List<NameChangeRequest> pendingRequests = dbHelper.getPendingNameChangeRequests();

        if (pendingRequests.isEmpty()) {
            Toast.makeText(this, "No pending requests.", Toast.LENGTH_SHORT).show();
        } else {
            // Initialize adapter and attach it to the RecyclerView
            adapter = new NameChangeRequestAdapter(pendingRequests, this::handleRequestAction);
            recyclerView.setAdapter(adapter);
        }
    }










    private void handleRequestAction(NameChangeRequest request, boolean isApproved) {
        boolean success;
        String title, message;

        // Log the incoming request
        Log.d("AdminNameRequest", "Handling request for: " + request.getEmail());

        if (isApproved) {
            success = dbHelper.approveNameChangeRequest(request.getId(), request.getEmail(),
                    request.getNewFirstName(), request.getNewLastName());

            if (success) {
                title = "Name Change Approved";
                message = "Your name change request has been approved.";
                Log.d("AdminNameRequest", "Adding notification: Title=" + title + ", Message=" + message);
                dbHelper.addNotification(title, message, request.getEmail(), "NameChange");
                Toast.makeText(this, "Request approved!", Toast.LENGTH_SHORT).show();
            }
        } else {
            success = dbHelper.rejectNameChangeRequest(request.getId());

            if (success) {
                title = "Name Change Rejected";
                message = "Your name change request has been rejected.";
                Log.d("AdminNameRequest", "Adding notification: Title=" + title + ", Message=" + message);
                dbHelper.addNotification(title, message, request.getEmail(), "NameChange");
                Toast.makeText(this, "Request rejected!", Toast.LENGTH_SHORT).show();
            }
        }

        if (!success) {
            Log.e("AdminNameRequest", "Failed to handle request action for ID: " + request.getId());
        }

        // Refresh RecyclerView data
        loadPendingRequests();
    }















//    private void handleRequestAction(NameChangeRequest request, boolean isApproved) {
//        boolean success;
//        if (isApproved) {
//            success = dbHelper.approveNameChangeRequest(request.getId(), request.getEmail(),
//                    request.getNewFirstName(), request.getNewLastName());
//            if (success) {
//                Toast.makeText(this, "Name change request approved!", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            success = dbHelper.rejectNameChangeRequest(request.getId()); // Pass only requestId
//            if (success) {
//                Toast.makeText(this, "Name change request rejected!", Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        // Refresh RecyclerView data
//        loadPendingRequests();
//    }

}
