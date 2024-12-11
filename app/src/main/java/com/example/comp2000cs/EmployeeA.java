package com.example.comp2000cs;

public class EmployeeA {
    public int id;
    public String firstname;
    public String lastname;
    public String email;
    public String department;
    public double salary;
    public String joiningdate;
    public int leaves;

    // Constructor
    public EmployeeA(int id, String firstname, String lastname, String email, String department, double salary, String joiningdate, int leaves) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.department = department;
        this.salary = salary;
        this.joiningdate = joiningdate;
        this.leaves = leaves;
    }
}
