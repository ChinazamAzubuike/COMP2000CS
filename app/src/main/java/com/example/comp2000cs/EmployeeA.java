package com.example.comp2000cs;

public class EmployeeA {
    private final int id;
    private final String firstname;
    private final String lastname;
    private final String email;
    private final String department;
    private final String joiningdate;
    private final int leaves;
    private final double salary;


    public EmployeeA(int id, String firstname, String lastname, String email, String department, String joiningdate, int leaves, double salary) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.department = department;
        this.joiningdate = joiningdate;
        this.leaves = leaves;
        this.salary = salary;
    }


    public int getId() { return id; }
    public String getFirstname() { return firstname; }
    public String getLastname() { return lastname; }
    public String getEmail() { return email; }
    public String getDepartment() { return department; }
    public String getJoiningdate() { return joiningdate; }
    public int getLeaves() { return leaves; }
    public double getSalary() { return salary; }
}
