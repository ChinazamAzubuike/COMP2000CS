package com.example.comp2000cs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LeaveRequestAdapter extends RecyclerView.Adapter<LeaveRequestAdapter.ViewHolder> {
    private final List<HolidayRequest> requests;
    private final RequestActionListener listener;

    public interface RequestActionListener {
        void onAction(HolidayRequest request, boolean isApproved);
    }

    public LeaveRequestAdapter(List<HolidayRequest> requests, RequestActionListener listener) {
        this.requests = requests;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leave_request, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HolidayRequest request = requests.get(position);
        holder.bind(request, listener);
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView emailView;
        private final TextView datesView;
        private final TextView totalDaysView;
        private final Button approveButton;
        private final Button rejectButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            emailView = itemView.findViewById(R.id.emailView);
            datesView = itemView.findViewById(R.id.datesView);
            totalDaysView = itemView.findViewById(R.id.totalDaysView);
            approveButton = itemView.findViewById(R.id.approveButton);
            rejectButton = itemView.findViewById(R.id.rejectButton);
        }

        public void bind(HolidayRequest request, RequestActionListener listener) {
            emailView.setText(request.getEmail());
            datesView.setText("Dates: " + request.getStartDate() + " to " + request.getEndDate());
            totalDaysView.setText("Total Days: " + request.getTotalDays());

            approveButton.setOnClickListener(v -> listener.onAction(request, true));
            rejectButton.setOnClickListener(v -> listener.onAction(request, false));
        }
    }
}
