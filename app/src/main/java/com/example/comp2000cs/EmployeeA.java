package com.example.comp2000cs;

public class EmployeeA {
    private final int id;
    private String firstname;
    private String lastname;
    private String email;
    private String department;
    private String joiningdate;
    private int leaves;
    private double salary;


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





    // Setters
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setJoiningdate(String joiningdate) {
        this.joiningdate = joiningdate;
    }

    public void setLeaves(int leaves) {
        this.leaves = leaves;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
