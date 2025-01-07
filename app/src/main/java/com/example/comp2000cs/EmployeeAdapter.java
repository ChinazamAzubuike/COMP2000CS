package com.example.comp2000cs;

import android.content.Context;
import android.content.Intent;
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
    private final DatabaseMaterial databaseMaterial; // Database helper
    private final Context context;

    // Constructor
    public EmployeeAdapter(Context context, List<EmployeeA> employeeList) {
        this.context = context;
        this.employeeList = employeeList;
        this.databaseMaterial = new DatabaseMaterial(context); // Initialize the database helper with context
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

        holder.firstNameTextView.setText(context.getString(R.string.employee_name, employee.getFirstname(), employee.getLastname()));
        holder.departmentTextView.setText(context.getString(R.string.employee_department, employee.getDepartment()));

        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditEmployee.class);
            intent.putExtra("employeeId", employee.getId()); // Pass the employee ID to the Edit screen
            context.startActivity(intent);
        });

        holder.btnDelete.setOnClickListener(v -> {
            databaseMaterial.deleteEmployee(employee.getId());
            employeeList.remove(position);
            notifyItemRemoved(position);
            Toast.makeText(context, "Deleted Employee: " + employee.getId(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View btnEdit, btnDelete;
        TextView firstNameTextView, departmentTextView;

        ViewHolder(View itemView) {
            super(itemView);
            firstNameTextView = itemView.findViewById(R.id.textViewFirstName);
            departmentTextView = itemView.findViewById(R.id.textViewDepartment);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
























//OLD
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.List;
//
//public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {
//
//    private final List<EmployeeA> employeeList;
//    private final DatabaseMaterial databaseMaterial; // Database helper
//
//    // Constructor
//    public EmployeeAdapter(Context context, List<EmployeeA> employeeList) {
//        this.context = context;
//        this.employeeList = employeeList;
//        this.databaseMaterial = new DatabaseMaterial(context); // Initialize the database helper
//    }
//
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_employee, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        EmployeeA employee = employeeList.get(position);
//
////        holder.idTextView.setText(holder.itemView.getContext().getString(R.string.employee_id, employee.getId()));
//        holder.firstNameTextView.setText(context.getString(R.string.employee_name, employee.getFirstname(), employee.getLastname()));
////        holder.emailTextView.setText(holder.itemView.getContext().getString(R.string.employee_email, employee.getEmail()));
//        holder.departmentTextView.setText(context.getString(R.string.employee_department, employee.getDepartment()));
////        holder.joiningDateTextView.setText(holder.itemView.getContext().getString(R.string.employee_joining_date, employee.getJoiningdate()));
////        holder.leavesTextView.setText(holder.itemView.getContext().getString(R.string.employee_leaves, employee.getLeaves()));
////        holder.salaryTextView.setText(holder.itemView.getContext().getString(R.string.employee_salary, employee.getSalary()));
//
//        holder.btnEdit.setOnClickListener(v -> {
//            private final Context context;
//            Intent intent = new Intent(context, EditEmployee.class);
//            intent.putExtra("employeeId", employee.getId()); // Pass the employee ID to the Edit screen
//            context.startActivity(intent);
//        });
//
//        holder.btnDelete.setOnClickListener(v -> {
//            //remove the employee safely
//            databaseMaterial.deleteEmployee(employee.getId()); // Delete from database
//            employeeList.remove(position); // Remove from the list
//            notifyItemRemoved(position);  // Notify RecyclerView of the change
//            Toast.makeText(holder.itemView.getContext(), "Deleted Employee: " + employee.getId(), Toast.LENGTH_SHORT).show();
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return employeeList.size();
//    }
//
//    // ViewHolder Class - made private static
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        public View btnEdit, btnDelete;
//        TextView idTextView, firstNameTextView, lastNameTextView, emailTextView;
//        TextView departmentTextView, joiningDateTextView, leavesTextView, salaryTextView;
//
//        ViewHolder(View itemView) {
//            super(itemView);
////            idTextView = itemView.findViewById(R.id.textViewId);
//            firstNameTextView = itemView.findViewById(R.id.textViewFirstName);
////            lastNameTextView = itemView.findViewById(R.id.textViewLastName);
////            emailTextView = itemView.findViewById(R.id.textViewEmail);
//            departmentTextView = itemView.findViewById(R.id.textViewDepartment);
////            joiningDateTextView = itemView.findViewById(R.id.textViewJoiningDate);
////            leavesTextView = itemView.findViewById(R.id.textViewLeaves);
////            salaryTextView = itemView.findViewById(R.id.textViewSalary);
//            btnEdit = itemView.findViewById(R.id.btnEdit);
//            btnDelete = itemView.findViewById(R.id.btnDelete);
//        }
//    }
//}