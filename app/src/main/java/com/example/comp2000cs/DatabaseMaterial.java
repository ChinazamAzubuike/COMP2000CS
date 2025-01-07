package com.example.comp2000cs;

import android.app.Notification;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class DatabaseMaterial extends SQLiteOpenHelper {



    // Database and Table Info
    private static final String DATABASE_NAME = "employees.db";
    private static final int DATABASE_VERSION = 7;
    private static final String TABLE_EMPLOYEES = "employees";
    private static final String TABLE_NOTIFICATIONS = "notifications";


    private static final String NOTIFICATION_ID = "id";
    private static final String NOTIFICATION_TITLE = "title";
    private static final String NOTIFICATION_MESSAGE = "message";
    private static final String NOTIFICATION_RECIPIENT = "recipientEmail";
    private static final String NOTIFICATION_TIMESTAMP = "timestamp";
    private static final String NOTIFICATION_TYPE = "type";

    // Columns
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FIRSTNAME = "firstname";
    private static final String COLUMN_LASTNAME = "lastname";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_DEPARTMENT = "department";
    private static final String COLUMN_JOINING_DATE = "joiningdate";
    private static final String COLUMN_LEAVES = "leaves";
    private static final String COLUMN_SALARY = "salary";

    Context context;

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
                "(1651, 'Mena', 'Azubui', 'menazubui@example.com', 'HR', '2020-09-10', 30, 48000.0), " +
                "(2953, 'Damoy', 'Azubui', 'damoy@example.com', 'IT', '2020-07-12', 30, 40000.0)");

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
                "('menazubui@example.com', 'password123', 0), " +
                "('damoy@example.com', 'password123', 0)");






        String CREATE_NOTIFICATIONS_TABLE = "CREATE TABLE " + TABLE_NOTIFICATIONS + " (" +
                NOTIFICATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NOTIFICATION_TITLE + " TEXT, " +
                NOTIFICATION_MESSAGE + " TEXT, " +
                NOTIFICATION_RECIPIENT + " TEXT, " +
                NOTIFICATION_TYPE + " TEXT, " + // Added 'type' column
                NOTIFICATION_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(CREATE_NOTIFICATIONS_TABLE);

        Log.d("DatabaseMaterial", "Tables created successfully.");







        String CREATE_NAME_CHANGE_REQUESTS_TABLE = "CREATE TABLE name_change_requests (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "email TEXT, " +
                "newFirstName TEXT, " +
                "newLastName TEXT, " +
                "status TEXT)";
        db.execSQL(CREATE_NAME_CHANGE_REQUESTS_TABLE);


        //holidays table

        String CREATE_HOLIDAY_REQUESTS_TABLE = "CREATE TABLE holiday_requests (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "email TEXT, " +
                "startDate TEXT, " +
                "endDate TEXT, " +
                "totalDays INTEGER, " +
                "status TEXT)";
        db.execSQL(CREATE_HOLIDAY_REQUESTS_TABLE);






    }






    public void clearNotifications() {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete("notifications", null, null);
        Log.d("DatabaseMaterial", "Deleted " + rowsDeleted + " notifications.");
        db.close();
    }









    public void addNotification(String title, String message, String recipientEmail, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOTIFICATION_TITLE, title);
        values.put(NOTIFICATION_MESSAGE, message);
        values.put(NOTIFICATION_RECIPIENT, recipientEmail);
        values.put(NOTIFICATION_TYPE, type);

        long result = db.insert(TABLE_NOTIFICATIONS, null, values);

        if (result == -1) {
            Log.e("DatabaseMaterial", "Failed to insert notification");
        } else {
            Log.d("DatabaseMaterial", "Notification added successfully: " + title);
        }

        db.close();
    }











    // Method to fetch all notifications
    public List<EmployeeNotifications> getAllNotifications(String recipientEmail) {
        List<EmployeeNotifications> notifications = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_NOTIFICATIONS + " WHERE " + NOTIFICATION_RECIPIENT + " = ? ORDER BY " + NOTIFICATION_TIMESTAMP + " DESC",
                new String[]{recipientEmail});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(NOTIFICATION_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(NOTIFICATION_TITLE));
                String message = cursor.getString(cursor.getColumnIndexOrThrow(NOTIFICATION_MESSAGE));
                String timestamp = cursor.getString(cursor.getColumnIndexOrThrow(NOTIFICATION_TIMESTAMP));
                notifications.add(new EmployeeNotifications(id, title, message, timestamp));
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            Log.d("DatabaseMaterial", "No notifications found for: " + recipientEmail);
        }

        db.close();
        return notifications;
    }


    public void logNotificationTableSchema() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("PRAGMA table_info(" + TABLE_NOTIFICATIONS + ")", null);

        Log.d("DatabaseMaterial", "Schema of notifications table:");
        while (cursor.moveToNext()) {
            String columnName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            Log.d("DatabaseMaterial", "Column: " + columnName);
        }
        cursor.close();
        db.close();
    }



//    public void insertNotification(String title, String message) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("title", title);
//        values.put("message", message);
//        values.put("timestamp", System.currentTimeMillis());
//        db.insert("notifications", null, values);
//        db.close();
//    }





    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        // Drop older tables if they exist
//        db.execSQL("DROP TABLE IF EXISTS employees");
//        db.execSQL("DROP TABLE IF EXISTS users");
//        db.execSQL("DROP TABLE IF EXISTS name_change_requests");
//        db.execSQL("DROP TABLE IF EXISTS holiday_requests");
//        db.execSQL("DROP TABLE IF EXISTS notifications");
//        // Recreate the database
//        onCreate(db);
//        if (oldVersion < 6) { // Increment version if needed
//            db.execSQL("ALTER TABLE notifications ADD COLUMN recipientEmail TEXT");
//        }
//    }




    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 7) {
            // Drop the old notifications table and recreate it with the new schema
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATIONS);
            onCreate(db); // Recreate the database
        }
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




    public boolean addNameChangeRequest(String email, String newFirstName, String newLastName) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("newFirstName", newFirstName);
        values.put("newLastName", newLastName);
        values.put("status", "pending");

        long result = db.insert("name_change_requests", null, values);
        db.close();

        return result != -1; // Return true if the insert was successful
    }






    //approving name request
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

        // Trigger Notification
        triggerLocalNotification(email, "Name Change Approved", "Your name change request has been approved.");

        return true;
    }




    //rejecting name request

    public boolean rejectNameChangeRequest(int requestId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues requestValues = new ContentValues();
        requestValues.put("status", "rejected");
        int rowsAffected = db.update("name_change_requests", requestValues, "id = ?", new String[]{String.valueOf(requestId)});

        db.close();
        return rowsAffected > 0; // Return true if the request was successfully rejected
    }



    public List<HolidayRequest> getPendingHolidayRequests() {
        List<HolidayRequest> requests = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                "holiday_requests",
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
                String startDate = cursor.getString(cursor.getColumnIndexOrThrow("startDate"));
                String endDate = cursor.getString(cursor.getColumnIndexOrThrow("endDate"));
                int totalDays = cursor.getInt(cursor.getColumnIndexOrThrow("totalDays"));
                String status = cursor.getString(cursor.getColumnIndexOrThrow("status"));

                requests.add(new HolidayRequest(id, email, startDate, endDate, totalDays, status));
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return requests;
    }



    public boolean approveHolidayRequest(int requestId, String email, int totalDays) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Update employee leave days
        Cursor cursor = db.query(
                "employees",
                new String[]{"leaves"},
                "email = ?",
                new String[]{email},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            int remainingLeaves = cursor.getInt(cursor.getColumnIndexOrThrow("leaves"));

            if (remainingLeaves < totalDays) {
                cursor.close();
                db.close();
                return false; // Not enough leave days
            }

            ContentValues employeeValues = new ContentValues();
            employeeValues.put("leaves", remainingLeaves - totalDays);
            db.update("employees", employeeValues, "email = ?", new String[]{email});

            cursor.close();
        }

        // Approve holiday request
        ContentValues requestValues = new ContentValues();
        requestValues.put("status", "approved");
        db.update("holiday_requests", requestValues, "id = ?", new String[]{String.valueOf(requestId)});

        db.close();

        // Trigger Notification
        triggerLocalNotification(email, "Holiday request approved!", "Your holiday request has been approved.");

        return true;
    }




    public boolean rejectHolidayRequest(int requestId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues requestValues = new ContentValues();
        requestValues.put("status", "rejected");
        int rowsAffected = db.update("holiday_requests", requestValues, "id = ?", new String[]{String.valueOf(requestId)});

        db.close();
        return rowsAffected > 0; // Return true if the request was successfully rejected
    }




    private void triggerLocalNotification(String email, String title, String message) {
        // Save notification to database or send it directly as a local notification

        NotificationUtils.sendLocalNotification(email, title, message, context);
    }



    public boolean addHolidayRequest(String email, String startDate, String endDate, int totalDays) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("startDate", startDate);
        values.put("endDate", endDate);
        values.put("totalDays", totalDays);
        values.put("status", "pending");

        long result = db.insert("holiday_requests", null, values);
        db.close();

        return result != -1;
    }






    public int getRemainingLeaveDays(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Check for null email
        if (email == null || email.isEmpty()) {
            Log.e("DatabaseMaterial", "Email is null or empty in getRemainingLeaveDays");
            return 0; // Return 0 if email is invalid
        }

        Cursor cursor = db.query(
                "employees",
                new String[]{"leaves"},
                "email = ?",
                new String[]{email},
                null,
                null,
                null
        );

        int remainingLeaves = 0;
        if (cursor != null && cursor.moveToFirst()) {
            remainingLeaves = cursor.getInt(cursor.getColumnIndexOrThrow("leaves"));
            cursor.close();
        }

        db.close();
        return remainingLeaves;
    }








    public List<HolidayRequest> getHolidayRequestsByEmail(String email) {
        List<HolidayRequest> requests = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                "holiday_requests",
                null,
                "email = ?",
                new String[]{email},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                HolidayRequest request = new HolidayRequest(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("email")),
                        cursor.getString(cursor.getColumnIndexOrThrow("startDate")),
                        cursor.getString(cursor.getColumnIndexOrThrow("endDate")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("totalDays")),
                        cursor.getString(cursor.getColumnIndexOrThrow("status"))
                );
                requests.add(request);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return requests;
    }





    public void deleteHolidayRequest(int requestId, @Nullable String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Delete the holiday request
        db.delete("holiday_requests", "id = ?", new String[]{String.valueOf(requestId)});

        // If email is provided, recalculate leave days
        if (email != null) {
            Cursor cursor = db.rawQuery("SELECT SUM(totalDays) FROM holiday_requests WHERE email = ? AND status = 'approved'", new String[]{email});
            int usedLeaveDays = 0;
            if (cursor.moveToFirst()) {
                usedLeaveDays = cursor.getInt(0);
            }
            cursor.close();

            ContentValues values = new ContentValues();
            values.put("leaves", 30 - usedLeaveDays); // Assuming 30 is the yearly leave allowance
            db.update("employees", values, "email = ?", new String[]{email});
        }

        db.close();
    }













    // Edit an existing holiday request
    public boolean editHolidayRequest(int requestId, String email, String newStartDate, String newEndDate, int totalDays) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("startDate", newStartDate);
        values.put("endDate", newEndDate);
        values.put("totalDays", totalDays);
        values.put("status", "pending"); // Reset status to pending for admin review

        // Update the holiday request where id and email match
        int rowsUpdated = db.update("holiday_requests", values, "id = ? AND email = ?", new String[]{String.valueOf(requestId), email});
        db.close();

        return rowsUpdated > 0; // Return true if the update was successful
    }





    //for salary implementation

    public void applyAnnualSalaryIncrement() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(
                "employees",
                null,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String joiningDate = cursor.getString(cursor.getColumnIndexOrThrow("joiningdate"));
                double currentSalary = cursor.getDouble(cursor.getColumnIndexOrThrow("salary"));

                // Check if the employee has completed one year
                try {
                    LocalDate joining = LocalDate.parse(joiningDate);
                    LocalDate today = LocalDate.now();

                    if (ChronoUnit.YEARS.between(joining, today) >= 1) {
                        // Increment salary by 5%
                        double newSalary = currentSalary * 1.05;

                        // Update the salary in the database
                        ContentValues values = new ContentValues();
                        values.put("salary", newSalary);
                        db.update("employees", values, "id = ?", new String[]{String.valueOf(id)});
                    }
                } catch (Exception e) {
                    Log.e("DatabaseMaterial", "Error parsing joining date for employee ID: " + id, e);
                }
            } while (cursor.moveToNext());

            cursor.close();
        }
        db.close();
    }





}
