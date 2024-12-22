package com.example.comp2000cs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseMaterial extends SQLiteOpenHelper {



    // Database and Table Info
    private static final String DATABASE_NAME = "employees.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_EMPLOYEES = "employees";

    // Columns
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FIRSTNAME = "firstname";
    private static final String COLUMN_LASTNAME = "lastname";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_DEPARTMENT = "department";
    private static final String COLUMN_JOINING_DATE = "joiningdate";
    private static final String COLUMN_LEAVES = "leaves";
    private static final String COLUMN_SALARY = "salary";

    public DatabaseMaterial(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Employees Table
        String CREATE_EMPLOYEES_TABLE = "CREATE TABLE " + TABLE_EMPLOYEES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_FIRSTNAME + " TEXT, " +
                COLUMN_LASTNAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_DEPARTMENT + " TEXT, " +
                COLUMN_JOINING_DATE + " TEXT, " +
                COLUMN_LEAVES + " INTEGER, " +
                COLUMN_SALARY + " REAL)";
        db.execSQL(CREATE_EMPLOYEES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the old table and create a new one
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEES);
        onCreate(db);
    }

    // Insert an Employee
    public void addEmployee(EmployeeA employee) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, employee.getId());
        values.put(COLUMN_FIRSTNAME, employee.getFirstname());
        values.put(COLUMN_LASTNAME, employee.getLastname());
        values.put(COLUMN_EMAIL, employee.getEmail());
        values.put(COLUMN_DEPARTMENT, employee.getDepartment());
        values.put(COLUMN_JOINING_DATE, employee.getJoiningdate());
        values.put(COLUMN_LEAVES, employee.getLeaves());
        values.put(COLUMN_SALARY, employee.getSalary());
        db.insert(TABLE_EMPLOYEES, null, values);
        db.close();


        Log.d("DATABASE", "Employee added: " + employee.getId());
    }

    // Retrieve All Employees
    public List<EmployeeA> getAllEmployees()
    {
        List<EmployeeA> employeeList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_EMPLOYEES, null);

        if (cursor.moveToFirst()) {
            do {
                EmployeeA employee = new EmployeeA(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRSTNAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LASTNAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DEPARTMENT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JOINING_DATE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_LEAVES)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_SALARY))
                );
                employeeList.add(employee);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return employeeList;
    }

    // Delete an Employee by ID
//    public void deleteEmployee(int id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_EMPLOYEES, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
//        db.close();
//    }



    public EmployeeA getEmployeeById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("employees", null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            EmployeeA employee = new EmployeeA(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("firstname")),
                    cursor.getString(cursor.getColumnIndexOrThrow("lastname")),
                    cursor.getString(cursor.getColumnIndexOrThrow("email")),
                    cursor.getString(cursor.getColumnIndexOrThrow("department")),
                    cursor.getString(cursor.getColumnIndexOrThrow("joiningdate")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("leaves")),
                    cursor.getDouble(cursor.getColumnIndexOrThrow("salary"))
            );
            cursor.close();
            return employee;
        }
        return null;
    }


    public void deleteEmployee(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("employees", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Update Employee Data
    public void updateEmployee(EmployeeA employee) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRSTNAME, employee.getFirstname());
        values.put(COLUMN_LASTNAME, employee.getLastname());
        values.put(COLUMN_EMAIL, employee.getEmail());
        values.put(COLUMN_DEPARTMENT, employee.getDepartment());
        values.put(COLUMN_JOINING_DATE, employee.getJoiningdate());
        values.put(COLUMN_LEAVES, employee.getLeaves());
        values.put(COLUMN_SALARY, employee.getSalary());
        db.update(TABLE_EMPLOYEES, values, COLUMN_ID + "=?", new String[]{String.valueOf(employee.getId())});
        db.close();
    }

//    public void clearEmployees() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("DELETE FROM employees");
//        db.close();
//    }

    @Override
    public void close() {
        super.close(); // Ensures the database connection is closed
    }


    public void clearEmployees() {
    }
}
