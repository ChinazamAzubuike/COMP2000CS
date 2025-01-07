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

    public interface RequestActionListener {
        void onAction(NameChangeRequest request, boolean isApproved);
    }

    public NameChangeRequestAdapter(List<NameChangeRequest> requests, RequestActionListener listener) {
        this.requests = requests;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_name_change_request, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NameChangeRequest request = requests.get(position);
        holder.bind(request, listener);
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    public void updateList(List<NameChangeRequest> updatedRequests) {
        this.requests.clear();
        this.requests.addAll(updatedRequests);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView emailView;
        private final TextView nameView;
        private final Button approveButton;
        private final Button rejectButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            emailView = itemView.findViewById(R.id.emailView);
            nameView = itemView.findViewById(R.id.nameView);
            approveButton = itemView.findViewById(R.id.approveButton);
            rejectButton = itemView.findViewById(R.id.rejectButton);
        }

        public void bind(NameChangeRequest request, RequestActionListener listener) {
            emailView.setText(request.getEmail());
            nameView.setText("New Name: " + request.getNewFirstName() + " " + request.getNewLastName());

            approveButton.setOnClickListener(v -> listener.onAction(request, true));
            rejectButton.setOnClickListener(v -> listener.onAction(request, false));
        }
    }
}
