package com.example.comp2000cs;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdminNameRequest extends AppCompatActivity {
    private RecyclerView requestsRecyclerView;
    private NameChangeRequestAdapter adapter;
    private DatabaseMaterial dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_name_request);

        // Initialize Database Helper
        dbHelper = new DatabaseMaterial(this);

        // Initialize RecyclerView
        requestsRecyclerView = findViewById(R.id.requestsRecyclerView);
        requestsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch Pending Requests
        List<NameChangeRequest> pendingRequests = dbHelper.getPendingNameChangeRequests();
        if (pendingRequests.isEmpty()) {
            Toast.makeText(this, "No pending requests.", Toast.LENGTH_SHORT).show();
        } else {
            // Setup Adapter
            adapter = new NameChangeRequestAdapter(pendingRequests, this::handleRequestAction);
            requestsRecyclerView.setAdapter(adapter);
        }
    }

    private void handleRequestAction(NameChangeRequest request, boolean isApproved) {
        boolean success;
        if (isApproved) {
            success = dbHelper.approveNameChangeRequest(request.getId(), request.getEmail(),
                    request.getNewFirstName(), request.getNewLastName());
            if (success) {
                Toast.makeText(this, "Request approved!", Toast.LENGTH_SHORT).show();
            }
        } else {
            success = dbHelper.rejectNameChangeRequest(request.getId());
            if (success) {
                Toast.makeText(this, "Request rejected!", Toast.LENGTH_SHORT).show();
            }
        }

        // Refresh the RecyclerView
        List<NameChangeRequest> updatedRequests = dbHelper.getPendingNameChangeRequests();
        adapter.updateList(updatedRequests);
    }
}
