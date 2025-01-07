package com.example.comp2000cs;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SixthActivity extends AppCompatActivity {
    private DatabaseMaterial dbHelper;
    private EmployeeHolidayRequestAdapter adapter;
    private int remainingLeaveDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sixth);

        dbHelper = new DatabaseMaterial(this);

        // Retrieve the logged-in user's email
        String email = getIntent().getStringExtra("email");
        if (email == null || email.isEmpty()) {
            Toast.makeText(this, "Error: No email provided.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Fetch remaining leave days
        remainingLeaveDays = dbHelper.getRemainingLeaveDays(email);

        // Initialize views
        TextView remainingDaysText = findViewById(R.id.remainingLeaveDays);
        RecyclerView recyclerView = findViewById(R.id.holidayRequestsRecyclerView);

        remainingDaysText.setText("Remaining leave days: " + remainingLeaveDays);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch holiday requests from the database
        List<HolidayRequest> requests = dbHelper.getHolidayRequestsByEmail(email);
        adapter = new EmployeeHolidayRequestAdapter(requests, new EmployeeHolidayRequestAdapter.RequestActionListener() {
            @Override
            public void onEdit(HolidayRequest request) {
                // Navigate to edit page
                Intent intent = new Intent(SixthActivity.this, EditHolidayRequest.class);
                intent.putExtra("requestId", request.getId());
                intent.putExtra("email", email);
                startActivity(intent);
            }





            // In onDelete method of EmployeeHolidayRequestAdapter.RequestActionListener
            @Override
            public void onDelete(HolidayRequest request) {
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(() -> {
                    // Perform delete operation in the background
                    dbHelper.deleteHolidayRequest(request.getId(), email);

                    // Update UI on the main thread
                    runOnUiThread(() -> {
                        Toast.makeText(SixthActivity.this, "Holiday request deleted.", Toast.LENGTH_SHORT).show();
                        refreshRequests(email);
                    });
                });
            }






//            @Override
//            public void onDelete(HolidayRequest request) {
//                // Delete the request
//                dbHelper.deleteHolidayRequest(request.getId(), email); // Call the method with both requestId and email
//                Toast.makeText(SixthActivity.this, "Holiday request deleted.", Toast.LENGTH_SHORT).show();
//                refreshRequests(email); // Refresh the list after deletion
//            }
        });

        recyclerView.setAdapter(adapter);
    }

    private void refreshRequests(String email) {
        List<HolidayRequest> updatedRequests = dbHelper.getHolidayRequestsByEmail(email);
        adapter.updateRequests(updatedRequests);

        // Update remaining leave days
        remainingLeaveDays = dbHelper.getRemainingLeaveDays(email);
        TextView remainingDaysText = findViewById(R.id.remainingLeaveDays);
        remainingDaysText.setText("Remaining leave days: " + remainingLeaveDays);
    }
}
