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
    private static final int DATABASE_VERSION = 3;
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
        Log.d("Database", "Employees table created.");

        // Prepopulate Employees Table
        db.execSQL("INSERT INTO employees (id, firstname, lastname, email, department, joiningdate, leaves, salary) VALUES " +
                "(1649, 'China', 'Azub', 'chinazub@example.com', 'Software', '2020-06-17', 30, 42000.0), " +
                "(1648, 'Ebu', 'Azu', 'ebuazu@example.com', 'Hardware', '2022-03-15', 30, 40000.0), " +
                "(1650, 'Kamma', 'Azubu', 'kamazubu@example.com', 'Medical', '2020-06-07', 30, 44000.0), " +
                "(1651, 'Mena', 'Azubui', 'menazubui@example.com', 'HR', '2020-09-10', 30, 48000.0)");
        Log.d("Database", "Employees table prepopulated.");


        String CREATE_USERS_TABLE = "CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "email TEXT UNIQUE, " +
                "password TEXT, " +
                "isAdmin INTEGER)";
        db.execSQL(CREATE_USERS_TABLE);

        // Prepopulate Users Table with Admin and Employees
        db.execSQL("INSERT INTO users (email, password, isAdmin) VALUES " +
                "('admin@example.com', 'admin123', 1), " +
                "('ebuazu@example.com', 'password123', 0), " +
                "('chinazub@example.com', 'password123', 0), " +
                "('kamazubu@example.com', 'password123', 0), " +
                "('menazubui@example.com', 'password123', 0)");


        String CREATE_NAME_CHANGE_REQUESTS_TABLE = "CREATE TABLE name_change_requests (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "email TEXT, " +
                "newFirstName TEXT, " +
                "newLastName TEXT, " +
                "status TEXT)";
        db.execSQL(CREATE_NAME_CHANGE_REQUESTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if they exist
        db.execSQL("DROP TABLE IF EXISTS employees");
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS name_change_requests");
        // Recreate the database
        onCreate(db);
    }


    //to insert an employee
    public void addEmployee(EmployeeA employee) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Check if the employee already exists
        Cursor cursor = db.query(
                TABLE_EMPLOYEES,
                null,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(employee.getId())},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            Log.d("DATABASE", "Employee already exists with ID: " + employee.getId());
            cursor.close();
            return; // Exit the method to prevent duplicates
        }

        // Insert the employee if it doesn't exist
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

        cursor.close();
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



    public EmployeeA getEmployeeById(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_EMPLOYEES,
                null,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
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
            cursor.close();
            return employee;
        }

        if (cursor != null) cursor.close();
        return null;
    }




    public EmployeeA getEmployeeByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                "employees", // Table name
                null,        // Columns (null means all)
                "email = ?", // WHERE clause
                new String[]{email}, // WHERE args
                null,        // GROUP BY
                null,        // HAVING
                null         // ORDER BY
        );

        EmployeeA employee = null;
        if (cursor != null && cursor.moveToFirst()) {
            employee = new EmployeeA(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("firstname")),
                    cursor.getString(cursor.getColumnIndexOrThrow("lastname")),
                    cursor.getString(cursor.getColumnIndexOrThrow("email")),
                    cursor.getString(cursor.getColumnIndexOrThrow("department")),
                    cursor.getString(cursor.getColumnIndexOrThrow("joiningdate")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("leaves")),
                    cursor.getDouble(cursor.getColumnIndexOrThrow("salary"))
            );
            Log.d("Database", "Employee found: " + employee.getFirstname() + " " + employee.getLastname());
            cursor.close();
        } else {
            Log.d("Database", "No employee found with email: " + email);
        }

        db.close();
        return employee;
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

    public void clearEmployees() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_EMPLOYEES);
        db.close();
        Log.d("DATABASE", "All employees cleared from the database");
    }



    public boolean validateLogin(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                "users",
                null,
                "email = ? AND password = ?",
                new String[]{email, password},
                null,
                null,
                null
        );

        boolean isValid = cursor != null && cursor.moveToFirst();
        if (cursor != null) cursor.close();
        db.close();
        return isValid;
    }


    public boolean isAdmin(String email)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                "users",
                new String[]{"isAdmin"},
                "email = ?",
                new String[]{email},
                null,
                null,
                null
        );

        boolean isAdmin = false;
        if (cursor != null && cursor.moveToFirst()) {
            isAdmin = cursor.getInt(cursor.getColumnIndexOrThrow("isAdmin")) == 1;
            cursor.close();
        }
        db.close();
        return isAdmin;
    }



//for managing the admin approving/declining name changes
    public List<NameChangeRequest> getPendingNameChangeRequests() {
        List<NameChangeRequest> requests = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                "name_change_requests",
                null,
                "status = ?",
                new String[]{"pending"},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                String newFirstName = cursor.getString(cursor.getColumnIndexOrThrow("newFirstName"));
                String newLastName = cursor.getString(cursor.getColumnIndexOrThrow("newLastName"));

                requests.add(new NameChangeRequest(id, email, newFirstName, newLastName));
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return requests;
    }



    //approving request
    public boolean approveNameChangeRequest(int requestId, String email, String newFirstName, String newLastName) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Update the employee's name
        ContentValues employeeValues = new ContentValues();
        employeeValues.put("firstname", newFirstName);
        employeeValues.put("lastname", newLastName);
        db.update("employees", employeeValues, "email = ?", new String[]{email});

        // Mark the request as approved
        ContentValues requestValues = new ContentValues();
        requestValues.put("status", "approved");
        db.update("name_change_requests", requestValues, "id = ?", new String[]{String.valueOf(requestId)});

        db.close();
        return true;
    }




    //rejecting request

    public boolean rejectNameChangeRequest(int requestId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Mark the request as rejected
        ContentValues requestValues = new ContentValues();
        requestValues.put("status", "rejected");
        db.update("name_change_requests", requestValues, "id = ?", new String[]{String.valueOf(requestId)});

        db.close();
        return true;
    }







}
