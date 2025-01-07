package com.example.comp2000cs;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SalaryIncrementWorker extends Worker {

    public SalaryIncrementWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        try (DatabaseMaterial dbHelper = new DatabaseMaterial(getApplicationContext())) {
            List<EmployeeA> employees = dbHelper.getAllEmployees();
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            for (EmployeeA employee : employees) {
                LocalDate joiningDate = LocalDate.parse(employee.getJoiningdate(), formatter);
                if (currentDate.isAfter(joiningDate.plusYears(1))) {
                    double updatedSalary = employee.getSalary() * 1.05; // Increment by 5%
                    employee.setSalary(updatedSalary);
                    dbHelper.updateEmployee(employee);
                }
            }
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }
}
