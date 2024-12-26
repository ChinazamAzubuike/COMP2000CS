package com.example.comp2000cs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NameChangeRequestAdapter extends RecyclerView.Adapter<NameChangeRequestAdapter.ViewHolder> {
    private final List<NameChangeRequest> requests;
    private final RequestActionListener listener;

    // Listener interface for handling actions (Approve/Reject)
    public interface RequestActionListener {
        void onAction(NameChangeRequest request, boolean isApproved);
    }

    // Constructor
    public NameChangeRequestAdapter(List<NameChangeRequest> requests, RequestActionListener listener) {
        this.requests = requests;
        this.listener = listener;
    }

    // Update list method
    public void updateList(List<NameChangeRequest> updatedRequests) {
        this.requests.clear();
        this.requests.addAll(updatedRequests);
        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_name_change_request, parent, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the current item
        NameChangeRequest request = requests.get(position);

        // Bind data to the view holder
        holder.bind(request, listener);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return requests.size();
    }

    // ViewHolder class to represent each item in the RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView emailView;
        private final TextView nameView;
        private final Button approveButton;
        private final Button rejectButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize views
            emailView = itemView.findViewById(R.id.emailView);
            nameView = itemView.findViewById(R.id.nameView);
            approveButton = itemView.findViewById(R.id.approveButton);
            rejectButton = itemView.findViewById(R.id.rejectButton);
        }

        // Bind data to the view
        public void bind(NameChangeRequest request, RequestActionListener listener) {
            // Use string resources for text
            emailView.setText(request.getEmail());
            nameView.setText(itemView.getContext().getString(R.string.new_name_placeholder,
                    request.getNewFirstName(), request.getNewLastName()));

            // Set click listeners
            approveButton.setOnClickListener(v -> listener.onAction(request, true));
            rejectButton.setOnClickListener(v -> listener.onAction(request, false));
        }
    }
}
