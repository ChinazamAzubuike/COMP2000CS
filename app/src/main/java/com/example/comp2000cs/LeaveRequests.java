package com.example.comp2000cs;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LeaveRequests extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LeaveRequestAdapter adapter;
    private DatabaseMaterial dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_requests);

        // Initialize database helper
        dbHelper = new DatabaseMaterial(this);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.leaveRequestsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load holiday requests
        loadHolidayRequests();
    }

    private void loadHolidayRequests() {
        List<HolidayRequest> pendingRequests = dbHelper.getPendingHolidayRequests();

        if (pendingRequests.isEmpty()) {
            Toast.makeText(this, "No pending leave requests.", Toast.LENGTH_SHORT).show();
        } else {
            // Initialize adapter and attach it to the RecyclerView
            adapter = new LeaveRequestAdapter(pendingRequests, this::handleRequestAction);
            recyclerView.setAdapter(adapter);
        }
    }







    private void handleRequestAction(HolidayRequest request, boolean isApproved) {
        boolean success;
        String title, message;
        if (isApproved) {
            success = dbHelper.approveHolidayRequest(request.getId(), request.getEmail(), request.getTotalDays());
            if (success) {
                title = "Holiday Request Approved";
                message = "Your holiday request has been approved.";
                dbHelper.addNotification(title, message, request.getEmail(), "Holiday");
                Toast.makeText(this, "Request approved!", Toast.LENGTH_SHORT).show();
            }
        } else {
            success = dbHelper.rejectHolidayRequest(request.getId());
            if (success) {
                title = "Holiday Request Rejected";
                message = "Your holiday request has been rejected.";
                dbHelper.addNotification(title, message, request.getEmail(), "Holiday");
                Toast.makeText(this, "Request rejected!", Toast.LENGTH_SHORT).show();
            }
        }

        // Refresh the list
        loadHolidayRequests();
    }












//    private void handleRequestAction(HolidayRequest request, boolean isApproved) {
//        boolean success;
//        if (isApproved) {
//            success = dbHelper.approveHolidayRequest(request.getId(), request.getEmail(), request.getTotalDays());
//            if (success) {
//                Toast.makeText(this, "Request approved!", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            success = dbHelper.rejectHolidayRequest(request.getId()); // Pass only requestId
//            if (success) {
//                Toast.makeText(this, "Request rejected!", Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        // Refresh the list
//        loadHolidayRequests();
//    }


}
