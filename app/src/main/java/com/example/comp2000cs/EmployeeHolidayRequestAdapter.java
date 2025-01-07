package com.example.comp2000cs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EmployeeHolidayRequestAdapter extends RecyclerView.Adapter<EmployeeHolidayRequestAdapter.ViewHolder> {
    private final List<HolidayRequest> requests;
    private final RequestActionListener listener;

    public interface RequestActionListener {
        void onEdit(HolidayRequest request);

        void onDelete(HolidayRequest request);
    }

    public EmployeeHolidayRequestAdapter(List<HolidayRequest> requests, RequestActionListener listener) {
        this.requests = requests;
        this.listener = listener;
    }

    public void updateRequests(List<HolidayRequest> updatedRequests) {
        this.requests.clear();
        this.requests.addAll(updatedRequests);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_employee_holiday_request, parent, false);
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
        private final TextView statusText;
        private final TextView datesText;
        private final TextView daysText;
        private final Button editButton;
        private final Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            statusText = itemView.findViewById(R.id.statusText);
            datesText = itemView.findViewById(R.id.datesText);
            daysText = itemView.findViewById(R.id.daysText);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }

        public void bind(HolidayRequest request, RequestActionListener listener) {
            statusText.setText("Status: " + request.getStatus());
            datesText.setText("Dates: " + request.getStartDate() + " to " + request.getEndDate());
            daysText.setText("Days: " + request.getTotalDays());

            editButton.setOnClickListener(v -> listener.onEdit(request));
            deleteButton.setOnClickListener(v -> listener.onDelete(request));
        }
    }
}
