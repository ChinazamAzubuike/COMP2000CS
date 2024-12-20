package com.example.comp2000cs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {

    private final List<EmployeeA> employeeList;

    // Constructor
    public EmployeeAdapter(List<EmployeeA> employeeList) {
        this.employeeList = employeeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_employee, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EmployeeA employee = employeeList.get(position);

        holder.idTextView.setText(holder.itemView.getContext().getString(R.string.employee_id, employee.getId()));
        holder.firstNameTextView.setText(holder.itemView.getContext().getString(R.string.employee_name, employee.getFirstname(), employee.getLastname()));
        holder.emailTextView.setText(holder.itemView.getContext().getString(R.string.employee_email, employee.getEmail()));
        holder.departmentTextView.setText(holder.itemView.getContext().getString(R.string.employee_department, employee.getDepartment()));
        holder.joiningDateTextView.setText(holder.itemView.getContext().getString(R.string.employee_joining_date, employee.getJoiningdate()));
        holder.leavesTextView.setText(holder.itemView.getContext().getString(R.string.employee_leaves, employee.getLeaves()));
        holder.salaryTextView.setText(holder.itemView.getContext().getString(R.string.employee_salary, employee.getSalary()));

        holder.btnEdit.setOnClickListener(v -> {
            // Open an EditEmployeeActivity or Dialog
            Toast.makeText(holder.itemView.getContext(), "Edit Employee: " + employee.getId(), Toast.LENGTH_SHORT).show();
        });

        // Handle Delete button click
        holder.btnDelete.setOnClickListener(v -> {
            employeeList.remove(position); // Remove the employee from the list
            notifyItemRemoved(position); // Notify RecyclerView about the change
            Toast.makeText(holder.itemView.getContext(), "Deleted Employee: " + employee.getId(), Toast.LENGTH_SHORT).show();
        });





    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    // ViewHolder Class - made private static
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View btnEdit, btnDelete;
        TextView idTextView, firstNameTextView, lastNameTextView, emailTextView;
        TextView departmentTextView, joiningDateTextView, leavesTextView, salaryTextView;

        ViewHolder(View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.textViewId);
            firstNameTextView = itemView.findViewById(R.id.textViewFirstName);
            lastNameTextView = itemView.findViewById(R.id.textViewLastName);
            emailTextView = itemView.findViewById(R.id.textViewEmail);
            departmentTextView = itemView.findViewById(R.id.textViewDepartment);
            joiningDateTextView = itemView.findViewById(R.id.textViewJoiningDate);
            leavesTextView = itemView.findViewById(R.id.textViewLeaves);
            salaryTextView = itemView.findViewById(R.id.textViewSalary);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}