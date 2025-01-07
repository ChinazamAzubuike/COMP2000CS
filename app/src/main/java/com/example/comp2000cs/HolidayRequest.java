package com.example.comp2000cs;

//this page is for employees
public class HolidayRequest {
    private int id;
    private String email;
    private String startDate;
    private String endDate;
    private int totalDays;
    private String status;

    public HolidayRequest(int id, String email, String startDate, String endDate, int totalDays, String status) {
        this.id = id;
        this.email = email;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalDays = totalDays;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
